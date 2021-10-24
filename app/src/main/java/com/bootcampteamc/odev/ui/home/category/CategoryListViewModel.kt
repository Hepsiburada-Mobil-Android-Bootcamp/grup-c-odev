package com.bootcampteamc.odev.ui.home.category

import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.infra.BaseViewModel
import com.bootcampteamc.odev.ui.home.HomeFragmentDirections

class CategoryListViewModel() : BaseViewModel() {


    var itemClickListener: (String) -> Unit = {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
        navigation.navigate(action)
    }


}