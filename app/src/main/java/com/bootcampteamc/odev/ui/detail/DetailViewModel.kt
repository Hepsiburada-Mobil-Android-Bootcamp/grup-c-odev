package com.bootcampteamc.odev.ui.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcampteamc.FireService
import com.bootcampteamc.odev.data.Product
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.lang.Exception


class DetailViewModel(productId : String) : ViewModel() {
    private var _productId : String ?= null

     var totalPrice = MutableLiveData<String>()

    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product>
        get() {
            return _product
        }
    private val _productCount = MutableLiveData<Int>()
    private val _imageUrl = MutableLiveData<Uri>()
    val imageUrl : LiveData<Uri>
        get() = _imageUrl
    val productCount :LiveData<Int>
        get() = _productCount

    init {
        _productId = productId
        _productCount.value = 0
        getProduct()

    }
    private fun getProduct(){
        viewModelScope.launch {
            try {
                _product.value = FireService.getProductDetails(_productId!!)
                val ref = FirebaseStorage.getInstance().reference.child(_product.value?.image.toString())
                ref.downloadUrl.addOnSuccessListener {
                    _imageUrl.value = it
                }

            }
            catch (e: Exception){
                Log.e("Error on product loading",e.message.toString())
            }
        }
    }
    fun TotalPriceGet(){
        totalPrice.value = productCount.value?.times(product.value?.price!!).toString()
    }
    fun addProduct(){
        _productCount.value = _productCount.value?.plus(1)
        TotalPriceGet()
    }
    fun deleteProduct(){
        if(_productCount.value == 0){
            return
        }
        _productCount.value = _productCount.value?.minus(1)
        TotalPriceGet()
    }

}