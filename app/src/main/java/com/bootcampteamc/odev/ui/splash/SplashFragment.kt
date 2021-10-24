package com.bootcampteamc.odev.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.MainActivity
import com.bootcampteamc.odev.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // configuration for splash screen


            Handler(Looper.getMainLooper()).postDelayed({

                findNavController().navigate(R.id.onboardFragment)
            }, 2000)





        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser

        if (currentUser != null) { //check if the user already signed in, if its true, go to main activity of the application
            val intent = Intent(
                requireContext(),
                MainActivity::class.java
            )//uygulama home ekranÄ±na geÃ§iÅŸ yapÄ±lacak
            startActivity(intent)
            requireActivity().finish()
        } else { //if not signed in already, open sign up fragment
            findNavController().navigate(R.id.signUpFragment)
        }
    }*/

}