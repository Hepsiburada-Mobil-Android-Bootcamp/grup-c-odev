package com.bootcampteamc.odev

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:error")
fun TextInputLayout.error(@StringRes errorMessage: Int?) {

    error = errorMessage?.resolveAsString(context)
    isErrorEnabled = errorMessage != null
    Log.e("Here+",isErrorEnabled.toString())
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: Uri?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions())
            .into(imgView)
    }
}
