package com.develop.productlisting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.develop.productlisting.database.Repository
import com.develop.productlisting.ui.ProgressIndicator

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repo : Repository, private val listener : ProgressIndicator) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repo,listener) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}