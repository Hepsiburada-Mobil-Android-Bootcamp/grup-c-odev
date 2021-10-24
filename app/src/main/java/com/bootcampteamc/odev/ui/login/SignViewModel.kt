package com.bootcampteamc.odev.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bootcampteamc.odev.MainActivity
import com.bootcampteamc.odev.ui.login.validation.EmailValidator
import com.bootcampteamc.odev.ui.login.validation.PasswordValidator
import com.google.firebase.auth.FirebaseAuth

class SignViewModel {
    private val _signSuccesful = MutableLiveData<Boolean>()
    val signSuccesful : LiveData<Boolean> = _signSuccesful
    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()
    private val _emailErrorMessage = MutableLiveData<Int>()
    val emailErrorMessage: LiveData<Int> = _emailErrorMessage
    private val _passwordErrorMessage = MutableLiveData<Int>()
    val passwordErrorMessage: LiveData<Int> = _passwordErrorMessage
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val auth by lazy { FirebaseAuth.getInstance() } //getting instance of FirebaseAuth

    fun signUpClicked(){

        if(isEmailValid() && isPasswordValid()){
            auth.createUserWithEmailAndPassword(email.value.orEmpty(), password.value.orEmpty())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { //sign up succesful
                        _signSuccesful.value = true
                    }
                }
        }
    }

    fun signInClicked(){
        if(isEmailValid() && isPasswordValid()){
            auth.signInWithEmailAndPassword(email.value.orEmpty(), password.value.orEmpty())
                .addOnCompleteListener { task ->
                if (task.isSuccessful) { //sign in succesful
                    _signSuccesful.value = true
                }
            }
        }
    }

    private fun isEmailValid(): Boolean{
        _emailErrorMessage.value = emailValidator.validate(email.value.orEmpty())
        return _emailErrorMessage.value == null
    }

    private fun isPasswordValid(): Boolean{
        _passwordErrorMessage.value = passwordValidator.validate(password.value.orEmpty())
        return _passwordErrorMessage.value == null
    }
}