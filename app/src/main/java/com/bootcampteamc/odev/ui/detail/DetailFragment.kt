package com.bootcampteamc.odev.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bootcampteamc.odev.R

import androidx.lifecycle.ViewModelProvider
import android.view.*
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.databinding.FragmentDetailBinding
import com.google.firebase.firestore.FirebaseFirestore





class DetailFragment : Fragment() {

    private var product: Product? = null
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
       // binding.lifecycleOwner = this
        //val productId = DetailFragmentArgs.fromBundle(requireArguments()).productId
        val db = FirebaseFirestore.getInstance()
        val productId = "4fe289eb-6d86-4b06-8a1e-47e354be311a"


        val viewModelFactory = DetailViewModelFactory(productId)
        binding.viewModel = ViewModelProvider(this,viewModelFactory).get(DetailViewModel::class.java)

//        viewModel.product.observe(viewLifecycleOwner, Observer {
//            if(null != it){
//                binding.textViewGrower.text = it.grower
//                binding.textViewName.text = it.name
//                //binding.textViewDescription.text = it.description
//                binding.textViewCbd.text = it.cbdPercantage.toString() +"%"
//                binding.textViewThc.text = it.thcPercentage.toString() + "%"
//                binding.textViewPrice.text = it.price.toString()
//            }
//        })
        return  binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

}