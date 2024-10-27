package com.develop.productlisting.viewmodel

import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.productlisting.R
import com.develop.productlisting.api.ProductApi
import com.develop.productlisting.api.RetrofitApi
import com.develop.productlisting.data.Constansts
import com.develop.productlisting.data.Product
import com.develop.productlisting.database.Repository
import com.develop.productlisting.ui.FilterType
import com.develop.productlisting.ui.ProgressIndicator
import com.develop.productlisting.ui.ProgressMode
import kotlinx.coroutines.launch

class MainViewModel(private val productRepo : Repository,
    private val listener : ProgressIndicator) : ViewModel() {

//    private var dao : IProductDao = productRepo.dao

    var filterType : FilterType = FilterType.NONE
        private set

    private var currentPage = 0
    private val pageSize = 20

    private var _items = MutableLiveData<List<Product>>()
    val prodItem : LiveData<List<Product>> get() =  _items

    private var itemCount : Int = 0
    var itemCountMessage =  MutableLiveData<String>()

    fun loadItems() {
        if(itemCount <= 0){
            itemCount = productRepo.getItemCount()
            itemCountMessage.value = "$itemCount Products found"
        }

        val offset = currentPage * pageSize
        val itemList : List<Product> = when(filterType) {
            FilterType.NONE -> {
                productRepo.getItemData(pageSize,offset)
            }
            FilterType.LOW_TO_HIGH -> {
                productRepo.getByFilterLowToHigh(pageSize,offset)
            }
            FilterType.HIGH_TO_LOW-> {
                productRepo.getByFilterHighToLow(pageSize,offset)
            }
            FilterType.ASCEND_BY_NAME -> {
                productRepo.getByNameAscending(pageSize,offset)
            }
            FilterType.DESCEND_BY_NAME -> {
                productRepo.getByNameDescending(pageSize,offset)
            }
        }
        if(itemList.isNotEmpty()){
            Log.d(Constansts.LOG_PRODUCT_DATA,"${filterType} :- ${itemList.size}")
            _items.value = itemList
            currentPage++
        }
    }

    fun resetPagination() {
        currentPage = 0;
    }


    fun fetchDataAndStoreIntoDB() {
//        https://baseball-test.shopfast.com/api/v1/catalog/products/app-products
        val retro = RetrofitApi.getRetroInstance(RetrofitApi.BASE_URL + "/api/v1/")
        viewModelScope.launch {
            val result = callApi(retro)
            result.fold(onSuccess = {
                    listener.dismissProgressbar(ProgressMode.DISMISS_DIALOG,"")
                    productRepo.insertIntoDb(it)
                    loadItems()

                },
                onFailure = {
                    listener.dismissProgressbar(ProgressMode.DISMISS_DIALOG,it.message.toString())
                    Log.d("TAG", "fetchDataAndStoreIntoDB: ${it.message}")
                })
        }
    }

    private suspend fun callApi(retro : ProductApi) : Result<List<Product>>{
        return try {
            val response = retro.fetchProduct()
            Result.success(response)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    fun setFilterType(type: FilterType) {
        filterType = type
    }

    fun isNetworkAvailable(context : Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(network) ?: return false

            return when{
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        }else{
            val activeNetwork = cm.isDefaultNetworkActive
            return activeNetwork
        }
    }


}