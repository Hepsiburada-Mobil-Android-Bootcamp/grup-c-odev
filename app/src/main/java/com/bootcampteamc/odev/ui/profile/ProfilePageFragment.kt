package com.bootcampteamc.odev.ui.profile

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.data.ProfileData
import com.bootcampteamc.odev.databinding.FragmentProfilePageBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class ProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel = ProfilePageViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=false
        firebaseAuth = FirebaseAuth.getInstance()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // to edit profile page
        binding.editButton.setOnClickListener {
           findNavController().navigate(ProfilePageFragmentDirections.actionProfilePageFragmentToProfileEditFragment())
        }

        // Loading data
        viewModel.getUser()
        viewModel.loadProfileInfo()

        return binding.root
    }

    // to upload the image saved


    override fun onStop() {
        super.onStop()
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=true
    }
}