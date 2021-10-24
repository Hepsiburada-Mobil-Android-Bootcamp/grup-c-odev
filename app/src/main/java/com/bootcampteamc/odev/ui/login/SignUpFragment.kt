package com.bootcampteamc.odev.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.MainActivity
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private val viewModel = SignViewModel()
    private val binding: FragmentSignUpBinding by lazy { //binding
        FragmentSignUpBinding.inflate(
            layoutInflater
        )
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val str = "Already a member? Log in"
        val spannableString = SpannableString(str) //adding clickable textview
        val clickableSpanLogin: ClickableSpan = object : ClickableSpan() { //adding onClick function to textview
            override fun onClick(p0: View) {
                findNavController().navigate(R.id.signInFragment)
            }
        }
        spannableString.setSpan(clickableSpanLogin, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) //defining the clickable word of the textview
        binding.textViewClickable.setText(spannableString, TextView.BufferType.SPANNABLE)
        binding.textViewClickable.movementMethod = LinkMovementMethod.getInstance()
        binding.textViewTerms.movementMethod = LinkMovementMethod.getInstance()
    }

}