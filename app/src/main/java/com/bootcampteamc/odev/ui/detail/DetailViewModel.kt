package com.bootcampteamc.odev.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcampteamc.FireService
import com.bootcampteamc.odev.data.Product
import kotlinx.coroutines.launch
import java.lang.Exception


class DetailViewModel(productId : String) : ViewModel() {
    private var _productId : String ?= null

    private var _product = MutableLiveData<Product>()
    val product : LiveData<Product>
        get() {
            return _product
        }


    init {
        _productId = productId
        getProduct()

    }
    private fun getProduct(){
        viewModelScope.launch {
            try {
                _product.value = FireService.getProductDetails(_productId!!)

            }
            catch (e: Exception){
                Log.e("Error on product loading",e.message.toString())
            }
        }
    }
}