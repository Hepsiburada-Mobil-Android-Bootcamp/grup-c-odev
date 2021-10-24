package com.bootcampteamc.odev.ui.profile

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bootcampteamc.odev.data.ProfileData
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfilePageViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email
    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name
    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber : LiveData<String> = _phoneNumber
    private val _address = MutableLiveData<String>()
    val address : LiveData<String> = _address
    private val _imageUrl = MutableLiveData<Uri>()
    val imageUrl : LiveData<Uri> = _imageUrl

    private val firebaseAuth = FirebaseAuth.getInstance()

    // if user logged in with google, to get the name and e-mail
    fun getUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            _email.value = email!!
        }
    }

    // to load the profile info written
    fun loadProfileInfo() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()
        uid?.let {
            firestore.collection("profileInfo").document(it).get()
                .addOnSuccessListener {
                    val profil = it.toObject(ProfileData::class.java)
                        _name.value = profil?.name.orEmpty()
                        _phoneNumber.value =profil?.phoneNumber.orEmpty()
                        _address.value = profil?.address.orEmpty()
                        _email.value = firebaseAuth.currentUser?.email.orEmpty()
                        val ref = FirebaseStorage.getInstance().reference.child(profil?.imageAddress.toString())
                        ref.downloadUrl.addOnSuccessListener {
                            _imageUrl.value = it
                        }
                }
        }
    }
}