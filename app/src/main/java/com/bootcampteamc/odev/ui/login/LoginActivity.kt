package com.bootcampteamc.odev.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.ui.login.SignUpFragment
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val signUpFragment = SignUpFragment()
        val currentUser = auth.currentUser

        /*if (currentUser != null) { //check if the user already signed in, if its true, go to main activity of the application
            *//*val intent = Intent(
                applicationContext,
                MainActivity::class.java
            )//uygulama home ekranÄ±na geÃ§iÅŸ yapÄ±lacak
            startActivity(intent)
            finish()*//*
        } else { //if not signed in already, open sign up fragment
            openFragment(signUpFragment)
        }
*/
        openFragment(signUpFragment) //proje birlestirildikten sonra burayı sil
    }

    private fun openFragment(fragment: Fragment) { //function for opening fragments
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(R.id.frame_layout, fragment)
        manager.commitAllowingStateLoss()
    }
}


