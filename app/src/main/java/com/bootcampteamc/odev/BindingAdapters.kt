package com.bootcampteamc.odev

import android.util.Log
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:error")
fun TextInputLayout.error(@StringRes errorMessage: Int?) {

    error = errorMessage?.resolveAsString(context)
    isErrorEnabled = errorMessage != null
    Log.e("Here+",isErrorEnabled.toString())
}
