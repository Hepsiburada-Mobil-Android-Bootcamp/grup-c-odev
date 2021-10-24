package com.bootcampteamc


import android.os.ProxyFileDescriptorCallback
import android.util.Log
import com.bootcampteamc.odev.data.Product
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.callbackFlow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FireService{

    suspend fun getProductDetails(productId : String) : Product?{
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("test").whereEqualTo("id",productId).get().await().documents[0].toObject(Product::class.java)
        }
        catch (e: Exception){
            Log.e("Firebase",e.message.toString())
            null
        }

    }
    fun logOut(){
        FirebaseAuth.getInstance().signOut()
    }
    fun getUserEmail() : String {
        return FirebaseAuth.getInstance().currentUser?.email.toString()
    }
}