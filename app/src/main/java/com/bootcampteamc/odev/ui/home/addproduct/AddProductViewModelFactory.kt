package com.bootcampteamc.odev.ui.home.addproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

 class AddProductViewModelFactory (private val repo: AddProductRepo) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T =  AddProductViewModel(repo) as T


 }