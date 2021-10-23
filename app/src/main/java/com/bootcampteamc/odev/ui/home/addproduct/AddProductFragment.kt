package com.bootcampteamc.odev.ui.home.addproduct

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentAddProductBinding
import com.bootcampteamc.odev.infra.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddProductFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddProductViewModel by viewModels {
        AddProductViewModelFactory(AddProductRepo())
    }
    private var getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.uri = uri
            }
            binding.imageView.setImageURI(uri)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
        binding.apply {
            imageView.setOnClickListener {
                getContent.launch("image/*")
            }


        }

        viewModel.close.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    dismiss()
                }
            }
        })
        initCategoriesSpinner()

        return binding.root
    }


    private fun initCategoriesSpinner() {
        val categories = resources.getStringArray(R.array.Categories)
        val categoryAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, categories
        )
        binding.categorySpinner.adapter = categoryAdapter
    }


}