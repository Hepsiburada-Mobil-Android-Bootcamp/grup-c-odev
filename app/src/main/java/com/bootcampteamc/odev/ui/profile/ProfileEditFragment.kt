package com.bootcampteamc.odev.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentProfileEditBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FirebaseFirestore

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private var firestore: FirebaseFirestore? = null
    private lateinit var imageUri : Uri
    private var viewModel = ProfileEditViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=false

        firestore = FirebaseFirestore.getInstance()

        // upload image
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            imageUri = it
            binding.profileImage.setImageURI(it)
            viewModel.insertUri(it)
        }
        binding.uploadButton.setOnClickListener { getImage.launch("image/*") }

        // Save button for saving the info and image
        binding.saveButton.setOnClickListener {
            viewModel.save()
           findNavController().navigate(R.id.homeFragment)
        }
        // Cancel editing profile
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.profilePageFragment)
        }

        return binding.root
    }
}