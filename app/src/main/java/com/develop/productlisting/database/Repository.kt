package com.develop.productlisting.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.develop.productlisting.data.Product
import com.develop.productlisting.pagination.MainPagingSource

class Repository(val dao : IProductDao) {

//    val allProducts : LiveData<List<Product>?> get() = dao.getAllProduct()

    fun getItemData(page : Int,pageSize : Int): List<Product> {
        return dao.getPagedList(page,pageSize)
    }
    suspend fun insertIntoDb(product: List<Product>){
        dao.insertIntoDB(product)
    }


    fun getByFilterHighToLow(page : Int,pageSize : Int) : List<Product>{
        return dao.getProductByPriceHighToLow(page,pageSize)
    }

    fun getByFilterLowToHigh(page : Int,pageSize : Int) : List<Product>{
        return dao.getProductByPriceLowToHigh(page,pageSize)
    }

    fun getItemCount(): Int {
        return dao.getItemCount()
    }

    fun getByNameAscending(pageSize: Int, offset: Int): List<Product> {
        return dao.getProductByNameAtoZ(pageSize,offset)
    }

    fun getByNameDescending(pageSize: Int, offset: Int): List<Product> {
        return dao.getProductByNameZtoA(pageSize,offset)
    }
}
