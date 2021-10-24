package com.bootcampteamc.odev.ui.profile

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.databinding.FragmentProfilePageBinding
import com.google.firebase.auth.FirebaseAuth


class ProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        // to edit profile page
        binding.editButton.setOnClickListener {
           findNavController().navigate(ProfilePageFragmentDirections.actionProfilePageFragmentToProfileEditFragment())
        }

        // Loading data
        getUser()
        loadImage()
        loadProfileInfo()

        return binding.root
    }
    // if user logged in with google, to get the name and e-mail
    private fun getUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            binding.email.text = email

            val name = firebaseUser.displayName
            binding.name.text = name
        }
    }
    // to upload the image saved
    private fun loadImage() {
        val sharedPref = context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val encodedImage = sharedPref?.getString("encodedImage", "DEFAULT")
        if (encodedImage != "DEFAULT") {
            val imageBytes = Base64.decode(encodedImage, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.profileImage.setImageBitmap(decodedImage)
        }
    }

    // to load the profile info written
    private fun loadProfileInfo() {
        val sharedPrefer = context?.getSharedPreferences("sharedPrefer", Context.MODE_PRIVATE)
        val nameInfo = sharedPrefer?.getString("nameInfo", binding.name.text.toString())
        val phoneInfo = sharedPrefer?.getString("phoneInfo", binding.phoneNumber.text.toString())
        val emailInfo = sharedPrefer?.getString("emailInfo", binding.email.text.toString())
        val addressInfo = sharedPrefer?.getString("addressInfo", binding.address.text.toString())

        binding.name.text = nameInfo
        binding.phoneNumber.text = phoneInfo
        binding.address.text = addressInfo
        binding.email.text = emailInfo
    }
}