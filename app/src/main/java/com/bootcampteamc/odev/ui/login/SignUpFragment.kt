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
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private val binding: FragmentSignUpBinding by lazy { //binding
        FragmentSignUpBinding.inflate(
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInFragment = SignInFragment()
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

        binding.buttonSignUp.setOnClickListener { signUpClick() } //calling sign up function when sign up button clicked
        binding.editTextPassword.setOnClickListener { binding.textInputLayout.setError(null) } //clean errors when clicked on the password area
    }

    fun signUpClick() {
        val userEmail = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) { //check email and password are not empty
            if (userEmail.contains('@') && userEmail.contains('.')) { //check if email has @ and . in it
                if (password.length < 7) { //if password length shorter than 7 chars give error
                    binding.textInputLayout.setError("Password is too short.")
                } else if (password.length > 40) {//if password length longer than 40 chars give error
                    binding.textInputLayout.setError("Password is too long.")
                } else {
                    if (isPasswordValid(password)) {//check password requirements, if it is valid set all the errors to null
                        binding.editTextEmail.setError(null)
                        binding.textInputLayout.setError(null)
                        if (binding.checkbox.isChecked) {//check if the checkbox of the terms and conditions clicked
                            auth.createUserWithEmailAndPassword(userEmail, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) { //sign up succesful
                                        Toast.makeText(activity, "Welcome to Spliff", Toast.LENGTH_LONG)
                                            .show()
                                          val intent = Intent(activity, MainActivity::class.java)
                                          startActivity(intent)
                                          activity?.finish()
                                    }
                                }.addOnFailureListener { exception -> //sign up failed
                                    Toast.makeText(
                                        activity,
                                        exception.localizedMessage,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                        }else{ //checkbox of the terms and conditions not checked, give an error
                            Toast.makeText(activity, "Before you proceed, you must accept the terms and conditions.",Toast.LENGTH_LONG)
                                .show()
                        }
                    } else { //password syntax not valid, give an error
                        binding.textInputLayout.setError(
                            "Password must contain one digit, " +
                                    "one uppercase letter, one lowercase letter and one special character."
                        )
                    }
                }
            } else { //email syntax not valid, give an error
                binding.editTextEmail.setError("Email is invalid.")
            }
        } else if (userEmail.isEmpty() && password.isNotEmpty()) { //email is empty, give an error
            binding.editTextEmail.setError("Email is required.")
        } else if (password.isEmpty()) { //password is empty, give an error
            binding.textInputLayout.setError("Password is required.")
        }
    }

    fun isPasswordValid(password: String?): Boolean {
        password?.let {
            val regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+*=_-])" //creating regex for password syntax
            val matcher = Regex(regexPattern)

            return matcher.find(password) != null //checking password syntax with the created regex
        } ?: return false
    }


}