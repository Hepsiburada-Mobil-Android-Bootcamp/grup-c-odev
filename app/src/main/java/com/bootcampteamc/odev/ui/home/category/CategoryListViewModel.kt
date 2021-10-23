package com.bootcampteamc.odev.ui.home.category

import com.bootcampteamc.odev.infra.BaseViewModel

class CategoryListViewModel() : BaseViewModel() {


    var itemClickListener: (String) -> Unit = {
        //val action = HomeFragmentDirections.actionHomeFragmentToTestFragment(it)
       // navigation.navigate(action)
    }


}