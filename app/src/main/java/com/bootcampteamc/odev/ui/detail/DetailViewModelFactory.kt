package com.bootcampteamc.odev.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DetailViewModelFactory(
    private val productId : String
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}