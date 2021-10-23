package com.bootcampteamc.odev.ui.home.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.infra.BaseViewModel
import com.example.bootcamp_group_c.ui.home.addproduct.validation.NameValidator
import java.util.*


class AddProductViewModel(private val repo: AddProductRepo) : BaseViewModel() {
    var uri: Uri?=null
    private val validator = NameValidator()
    val category = MutableLiveData<Int>()
    val growerName = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val thc = MutableLiveData<String>()
    val cbd = MutableLiveData<String>()
    private val _close = MutableLiveData<Boolean>().apply { postValue(false)}
    private val _nameErrorMessage = MutableLiveData<Int>()
    private val _growerNameErrorMessage = MutableLiveData<Int>()
    private val categories = arrayListOf("Flowers","Vapes","Extracts","Edibles","Accessories")
    val nameErrorMessage: LiveData<Int> = _nameErrorMessage
    val growerNameErrorMessage: LiveData<Int> = _growerNameErrorMessage
    val close: LiveData<Boolean> = _close

    fun onAddButtonClick() {
        Log.e("Here","onClick")
        if(isNameValid() and isGrowerNameValid()){
            val image = uri?.let { repo.uploadImage(it) }
            val uniqueID = UUID.randomUUID().toString()
            val product = Product(uniqueID,
                name.value,
                growerName.value,
                description.value,
                thc.value?.toInt(),
                cbd.value?.toInt(),
                category.value?.let { categories.get(it) },
                price.value?.toInt(),
                image,
            )
            repo.addProduct(product )
            _close.value=true
        }
    }


    private fun isNameValid(): Boolean {
        _nameErrorMessage.value = validator.validate(name.value.orEmpty())
        return  _nameErrorMessage.value == null
    }

    private fun isGrowerNameValid() : Boolean {
        _growerNameErrorMessage.value = validator.validate(growerName.value.orEmpty())
        return  _growerNameErrorMessage.value == null
    }
}