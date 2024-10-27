package com.develop.productlisting.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.develop.productlisting.R
import com.develop.productlisting.adapter.ProductAdapter
import com.develop.productlisting.database.ProductDatabase
import com.develop.productlisting.database.Repository
import com.develop.productlisting.databinding.ActivityMainBinding
import com.develop.productlisting.databinding.ProgressViewBinding
import com.develop.productlisting.viewmodel.MainViewModel
import com.develop.productlisting.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), MyBottomSheetDialogFragment.OnSortOptionSelectedListener,ProgressIndicator {
    lateinit var mainBind : ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var productRepo : Repository

    private lateinit var adapter: ProductAdapter
    private lateinit var pDialog: AlertDialog

    private val locLauncher : ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            fetchProductFromDB()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Databases and Viewmodel
        val dao = ProductDatabase.getDatabase(this).getProductDao()
        productRepo = Repository(dao)
        mainViewModel = ViewModelProvider(this,ViewModelFactory(productRepo,this))[MainViewModel::class.java]
        mainViewModel.setFilterType(FilterType.NONE)

        // Icon setup
        mainBind.btnFilter.setImageResource(R.mipmap.ic_filter)

        // DataBinding
        mainBind.vm = mainViewModel
        mainBind.lifecycleOwner = this

        // Recyclerview Setup
        adapter = ProductAdapter()
        mainBind.recycleView.layoutManager = GridLayoutManager(this,2)
        mainBind.recycleView.adapter = adapter

        // Progressbar Setup
        configProgressDialog(this)


        mainViewModel.prodItem.observe(this, Observer {
            adapter.submitList(it)
        })

        attachScrollListener(mainBind.recycleView)

        mainBind.btnFilter.setOnClickListener {
            if(adapter.itemCount > 0){
                displayBottomSheet()
            }else{
                Toast.makeText(applicationContext, "Products not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // RuntimePermission from User and if granted then only fetch data
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            locLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else {
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("Permission is required to fetch products from server")
                    .setPositiveButton("Ok"){ dialogInterface, i ->
                        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package",packageName,null)
                        intent.data = uri
                        startActivity(intent)
                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("Cancel"){dia,_ ->
                        dia.dismiss()
                    }
                    .setCancelable(false)
                    .create()
                    .show()
            }else{
                // Fetch Product
                fetchProductFromDB()
            }
        }
    }

    private fun fetchProductFromDB() {
        if(mainViewModel.isNetworkAvailable(this)){
            showProgressbar(ProgressMode.SHOW_DIALOG)
            mainViewModel.fetchDataAndStoreIntoDB()
        }else{
            Toast.makeText(applicationContext, "Internet required..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun attachScrollListener(recyclerView: RecyclerView){
        recyclerView.addOnScrollListener(object  : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if(layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount-1){
                    mainViewModel.loadItems()
                }
            }
        })
    }

    private fun displayBottomSheet() {
        val bottomSheet = MyBottomSheetDialogFragment()
        val dataBundle = Bundle()
        dataBundle.putString("filterType",mainViewModel.filterType.toString())
        bottomSheet.arguments?.putBundle("bundleData",dataBundle)
        bottomSheet.show(supportFragmentManager,bottomSheet.tag)
    }

    override fun onSortOptionSelected(filterType: FilterType) {
        adapter.clearData()
        mainViewModel.resetPagination()
        mainViewModel.setFilterType(filterType)
        mainViewModel.loadItems()
    }

    private fun configProgressDialog(context: Context) {
        try {
            val view = ProgressViewBinding.inflate(LayoutInflater.from(context.applicationContext)).root
            pDialog = AlertDialog.Builder(context).setCancelable(false).setView(view).create()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    override fun showProgressbar(mode: ProgressMode) {
        if(!pDialog.isShowing && mode == ProgressMode.SHOW_DIALOG){
            pDialog.show()
        }
    }

    override fun dismissProgressbar(mode: ProgressMode, message : String) {
        if(pDialog.isShowing && mode == ProgressMode.DISMISS_DIALOG){
            if (message.isNotEmpty()){
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
            pDialog.dismiss()
        }
    }
}