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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignIn.setOnClickListener { //calling sign in function when sign in button clicked
            signInClick()
        }
    }

    fun signInClick() {
        val userEmail = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) {//check email and password are not empty
            if (userEmail.contains('@') && userEmail.contains('.') && //check if email has @ and . in it
                userEmail.length > 5 && userEmail.length < 50                  //and check length
            ) {
                binding.editTextEmail.setError(null)
                binding.textInputLayout.setError(null)
                auth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener { task ->

                    if (task.isSuccessful) { //sign in succesful
                        Toast.makeText(
                            activity,
                            "Welcome: ${auth.currentUser?.email.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                         val intent = Intent(activity, MainActivity::class.java)
                         startActivity(intent)
                         activity?.finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        activity,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                binding.editTextEmail.setError("Email is invalid.") //email syntax is invalid, give an error
            }
        } else if (userEmail.isEmpty() && password.isNotEmpty()) { //email is empty, give an error
            binding.editTextEmail.setError("Email is required.")
        } else if (password.isEmpty()) { //password is empty, give an error
            binding.textInputLayout.setError("Password is required.")
        }
    }
}