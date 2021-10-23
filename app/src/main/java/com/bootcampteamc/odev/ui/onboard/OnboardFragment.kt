package com.bootcampteamc.odev.ui.onboard

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentOnboardBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class OnboardFragment : Fragment() {
    private lateinit var binding: FragmentOnboardBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    // constant
    private companion object {
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOnboardBinding.inflate(inflater, container, false)

        // start the background animation
        val animationDrawable = binding.clOnboard.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        // google sign in
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // start function
        checkUser()

        // result launcher for google sign in
        val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                Log.d(TAG, "onActivityResult: Google SignIn intent result")
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = accountTask.getResult(ApiException::class.java)
                    firebaseAuthWithGoogleAccount(account)
                } catch (e: Exception) {
                    Log.d(TAG, "onActivityResult: ${e.message}")
                }
            }
        }
        // google sign in button
        binding.ibGoogle.setOnClickListener {
            Log.d(TAG, "onCreate: begin Google SignIn")
            resultLauncher.launch(Intent(googleSignInClient.signInIntent))
        }


        //login and sign in buttons
        /*
        binding.bLogin.setOnClickListener {

        }

        binding.bJoin.setOnClickListener {

        }
*/
        return binding.root
    }
    // check if user already signed in with google
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
         //   findNavController().navigate(R.id.profilePageFragment)
        }
    }


    // google sign with firebase
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn")
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")

                if(authResult.additionalUserInfo!!.isNewUser) {
                    // new user
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Account created... \n$email")
                    Toast.makeText(context, "Account created... \n$email", Toast.LENGTH_SHORT).show()
                 //   findNavController().navigate(R.id.profilePageFragment)
                } else {
                    // existing user
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Existing user... \n$email")
                    Toast.makeText(context, "LoggedIn... \n$email", Toast.LENGTH_SHORT).show()
                  //  findNavController().navigate(R.id.profilePageFragment)
                }
            }
            .addOnFailureListener { e ->
                // login failed
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Login Failed due to ${e.message}")
                Toast.makeText(context, "Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}