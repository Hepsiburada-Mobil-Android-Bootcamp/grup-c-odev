package com.bootcampteamc.odev.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.data.Product
import com.bootcampteamc.odev.data.ProfileData
import com.bootcampteamc.odev.databinding.FragmentProfileEditBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private var firestore: FirebaseFirestore? = null
    private lateinit var imageUri : Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)

        //baya kötü oldu ama ben yaptım -Çağrı
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).menu.findItem(R.id.searchFragment).isVisible=false
        //üstü
        firestore = FirebaseFirestore.getInstance()

        // upload image
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            imageUri = it
            binding.profileImage.setImageURI(it)
        }
        binding.uploadButton.setOnClickListener { getImage.launch("image/*") }

        // Save button for saving the info and image
        binding.saveButton.setOnClickListener {
            save()
           findNavController().navigate(R.id.profilePageFragment)
        }
        // Cancel editing profile
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.profilePageFragment)
        }

        return binding.root
    }

//    // save image with sharedPreferences
//    private fun saveImage() {
//        val sharedPref = context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        val baos = ByteArrayOutputStream()
//        val bitmap = binding.profileImage.drawable.toBitmap()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
//
//        with(sharedPref!!.edit()) {
//            putString("encodedImage", encodedImage)
//            apply()
//        }
//
//    }
//
//
//
//    // save the written info
//    private fun saveProfileInfo() {
//        val sharedPrefer =
//            activity?.getSharedPreferences("sharedPrefer", Context.MODE_PRIVATE) ?: return
//        with(sharedPrefer.edit()) {
//            if (binding.name.text.toString().isNotEmpty()) {
//                putString("nameInfo", binding.name.text.toString())
//            }
//            if (binding.phone.text.toString().isNotEmpty()) {
//                putString("phoneInfo", binding.phone.text.toString())
//            }
//            if (binding.email.text.toString().isNotEmpty()) {
//                putString("emailInfo", binding.email.text.toString())
//            }
//            if (binding.address.text.toString().isNotEmpty()) {
//                putString("addressInfo", binding.address.text.toString())
//            }
//            apply()
//        }
//    }
    private fun save() {
        val db = FirebaseFirestore.getInstance()
        val imageAdress = uploadImage(imageUri)
        val profile = ProfileData(
            uid = FirebaseAuth.getInstance().currentUser?.uid,
            name = binding.name.text.toString(),
            phoneNumber = binding.phone.text.toString().toInt(),
            email = binding.email.text.toString(),
            address = binding.address.text.toString(),
            imageAddress = imageAdress
        )

        if (profile.name.isNullOrEmpty() || profile.phoneNumber.toString().isNullOrEmpty()
            || profile.email.isNullOrEmpty() || profile.address.isNullOrEmpty()) {
            Toast.makeText(requireContext(),"FILL IN THE BLANKS..", Toast.LENGTH_LONG).show()
        } else {
            firestore?.collection("profileInfo")?.add(profile)?.addOnSuccessListener {
            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()

        }?.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
        }

    }
    fun uploadImage(uri: Uri) : String{
         val ref = FirebaseStorage.getInstance().reference
        val imageAdress = "images/"+ UUID.randomUUID().toString()
        ref.child(imageAdress).putFile(uri).addOnSuccessListener {
        }
        return imageAdress
    }

}