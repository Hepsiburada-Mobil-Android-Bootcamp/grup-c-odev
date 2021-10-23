package com.bootcampteamc.odev.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.databinding.ItemProductBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage


class ProductAdapter(query: Query, private val parentFragmentManager: FragmentManager) : FirestoreAdapter<ProductAdapter.ProductHolder >(query) {

    var itemClickListener: (String) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {

        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return ProductHolder(binding,parentFragmentManager ,itemClickListener)

    }

    class ProductHolder(
        private val binding: ItemProductBinding,
        private val parentFragmentManager: FragmentManager?,
        private val itemClickListener: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                growerName.text = product.grower
                productName.text= product.name
                price.text = "$"+product.price.toString()
                val ref = FirebaseStorage.getInstance().reference.child(product.image.toString())
                ref.downloadUrl.addOnSuccessListener {
                    Glide.with(binding.root.context).load(it).centerCrop().into(binding.productImage)
                }
                binding.root.setOnClickListener {
                    product.id?.let { id -> itemClickListener(id) }
                }
                appCompatImageButton.setOnClickListener {

                }
            }
        }


    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        getSnapshot(position)?.let { snapshot -> snapshot.toObject(Product::class.java)?.let {
            holder.bind(
                it
            )
        } }
    }


}