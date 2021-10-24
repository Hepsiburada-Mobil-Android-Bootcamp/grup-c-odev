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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=false
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


        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()
        uid?.let {
            firestore.collection("profileInfo").document(it).get()
                .addOnSuccessListener {
                    val profil = it.toObject(ProfileData::class.java)
                    binding.apply {
                        name.text = profil?.name.orEmpty()
                        phoneNumber.text =profil?.phoneNumber.orEmpty()
                        address.text = profil?.address.orEmpty()
                        email.text = firebaseAuth.currentUser?.email.orEmpty()
                        val ref = FirebaseStorage.getInstance().reference.child(profil?.imageAddress.toString())
                        ref.downloadUrl.addOnSuccessListener {
                            Glide.with(binding.root.context).load(it).centerCrop().into(binding.profileImage)
                        }

                    }
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }

        }



    }

    override fun onStop() {
        super.onStop()
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=true
    }
}