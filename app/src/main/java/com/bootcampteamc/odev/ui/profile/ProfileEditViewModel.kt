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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileEditViewModel : ViewModel() {

    var name = MutableLiveData<String>()

    var phoneNumber = MutableLiveData<String>()

    var address = MutableLiveData<String>()

    private val _imageUrl = MutableLiveData<Uri>()
    val imageUrl : LiveData<Uri> = _imageUrl

    private val firestore = FirebaseFirestore.getInstance()

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun save() {
        val imageAdress = _imageUrl.value?.let { uploadImage(it) }
        val profile = ProfileData(
            uid = firebaseAuth.currentUser?.uid,
            name = name.value,
            phoneNumber = phoneNumber.value,
            address = address.value,
            imageAddress = imageAdress
        )

        profile.uid?.let {
            firestore.collection("profileInfo")?.document(it)?.set(profile)
        }

        if (profile.name.isNullOrEmpty() || profile.phoneNumber.toString().isEmpty()
            || profile.address.isNullOrEmpty()) {
          //  Toast.makeText(requireContext(),"FILL IN THE BLANKS..", Toast.LENGTH_LONG).show()
        } else {
            profile.uid?.let {
                firestore.collection("profileInfo").document(it)?.set(profile)
            }
        }
    }
    fun uploadImage(uri: Uri) : String{
        val ref = FirebaseStorage.getInstance().reference
        val imageAdress = "images/"+ UUID.randomUUID().toString()
        ref.child(imageAdress).putFile(uri).addOnSuccessListener {
        }
        return imageAdress
    }

    fun insertUri(uri: Uri) {
        _imageUrl.value = uri
    }
}