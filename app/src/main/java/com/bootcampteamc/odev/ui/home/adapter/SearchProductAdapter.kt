package com.bootcampteamc.odev.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.databinding.ItemProductBinding
import com.google.firebase.firestore.Query
import com.yasincetin.firebasesdk.firestore.FirestoreAdapter

class SearchProductAdapter(query: Query) :
    FirestoreAdapter<ProductAdapter.ProductHolder, Product>(query) {
    override fun getModelClass() = Product::class.java
    override fun onBindViewHolder(holder: ProductAdapter.ProductHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductHolder {

        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return ProductAdapter.ProductHolder(binding, null) { }
    }
}