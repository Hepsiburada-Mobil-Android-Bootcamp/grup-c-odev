package com.bootcampteamc.odev.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.R

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // configuration for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
         //  findNavController().navigate(R.id.action_splashFragment_to_onboardFragment)
        }, 1000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}