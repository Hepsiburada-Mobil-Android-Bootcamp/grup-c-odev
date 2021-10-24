package com.bootcampteamc.odev.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bootcampteamc.odev.MainActivity
import com.bootcampteamc.odev.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private val viewModel = SignViewModel()
    private val binding: FragmentSignInBinding by lazy { //binding
        FragmentSignInBinding.inflate(
            layoutInflater
        )
    }
    private val auth by lazy { FirebaseAuth.getInstance() } //getting instance of FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.signSuccesful.observe(viewLifecycleOwner,{
            if(it){
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })


        return binding.root
    }
}