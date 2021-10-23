package com.bootcampteamc.odev.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.bootcampteamc.odev.databinding.FragmentProfileEditBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private var firestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()

        // upload image
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.profileImage.setImageURI(it)
        }
        binding.uploadButton.setOnClickListener { getImage.launch("image/*") }

        // Save button for saving the info and image
        binding.saveButton.setOnClickListener {
            saveProfileInfo()
            saveImage()
          //  findNavController().navigate(R.id.profilePageFragment)
        }
        // Cancel editing profile
        binding.cancelButton.setOnClickListener {
         //   findNavController().navigate(R.id.profilePageFragment)
        }

        return binding.root
    }

    // save image with sharedPreferences
    private fun saveImage() {
        val sharedPref = context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val baos = ByteArrayOutputStream()
        val bitmap = binding.profileImage.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

        with(sharedPref!!.edit()) {
            putString("encodedImage", encodedImage)
            apply()
        }

    }

    // save the written info
    private fun saveProfileInfo() {
        val sharedPrefer =
            activity?.getSharedPreferences("sharedPrefer", Context.MODE_PRIVATE) ?: return
        with(sharedPrefer.edit()) {
            if (binding.name.text.toString().isNotEmpty()) {
                putString("nameInfo", binding.name.text.toString())
            }
            if (binding.phone.text.toString().isNotEmpty()) {
                putString("phoneInfo", binding.phone.text.toString())
            }
            if (binding.email.text.toString().isNotEmpty()) {
                putString("emailInfo", binding.email.text.toString())
            }
            if (binding.address.text.toString().isNotEmpty()) {
                putString("addressInfo", binding.address.text.toString())
            }
            apply()
        }
    }
}