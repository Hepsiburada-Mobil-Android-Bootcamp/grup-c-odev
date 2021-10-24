package com.bootcampteamc.odev.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
}