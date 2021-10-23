package com.bootcampteamc.odev.ui.home.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bootcampteamc.odev.ui.home.addproduct.AddProductRepo
import com.bootcampteamc.odev.ui.home.addproduct.AddProductViewModel

class CategoryListViewModelFactory () : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =  CategoryListViewModel() as T


}