package com.bootcampteamc.odev.data

import com.yasincetin.firebasesdk.firestore.FirestoreModel

data class Product(
    val id: String? = "",
    val name: String? = "",
    val grower: String? = "",
    val description: String? = "",
    val thcPercentage: Int? = null,
    val cbdPercantage: Int? = null,
    val category: String? = null,
    val price: Int? = null,
    val image: String? = "",
) : FirestoreModel()
