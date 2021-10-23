package com.bootcampteamc.odev.ui.home.addproduct

import android.net.Uri
import com.bootcampteamc.odev.data.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddProductRepo {
    private val firestore = FirebaseFirestore.getInstance()
    private val ref = FirebaseStorage.getInstance().reference

    fun addProduct(product: Product) {
        firestore.collection("test").add(product).addOnSuccessListener {
        }.addOnFailureListener{
        }
    }
    fun uploadImage(uri: Uri) : String{
        val imageAdress = "images/"+ UUID.randomUUID().toString()
        ref.child(imageAdress).putFile(uri).addOnSuccessListener {
        }
        return imageAdress
    }


}