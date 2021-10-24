package com.bootcampteamc.odev.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bootcampteamc.odev.R

import androidx.lifecycle.ViewModelProvider
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.databinding.FragmentDetailBinding
import com.google.firebase.firestore.FirebaseFirestore





class DetailFragment : Fragment() {

    private var product: Product? = null
    val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var products : Product
        var productss : List<Product>
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val documentId = args.documentId

        val viewModelFactory = DetailViewModelFactory(documentId)
        binding.viewModel = ViewModelProvider(this,viewModelFactory).get(DetailViewModel::class.java)

      viewModel.product.observe(viewLifecycleOwner, Observer {
           if(null != it){
               binding.apply {
                   textViewGrower.text = it.grower
                   textViewName.text = it.name
                   textViewDescription.text = it.description
                   textViewCbd.text = it.cbdPercantage.toString() +"%"
                   textViewThc.text = it.thcPercentage.toString() + "%"
                   textViewPrice.text = "$"+ it.price.toString()
               }

           }
       })
        binding.addProduct.setOnClickListener {
            viewModel.addProduct()
        }
        binding.removeProduct.setOnClickListener {
            viewModel.deleteProduct()
        }
        binding.addToBag.setOnClickListener{
            Toast.makeText(requireContext(),"Products have been added to the bag. Happy Spliffing!",Toast.LENGTH_SHORT).show()
        }
        return  binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

}