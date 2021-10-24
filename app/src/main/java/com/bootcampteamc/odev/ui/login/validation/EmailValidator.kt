package com.bootcampteamc.odev.ui.login.validation

import android.text.TextUtils
import android.util.Patterns
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.ui.home.Validator

class EmailValidator : Validator {
    override fun validate(field: String) = when {
        TextUtils.isEmpty(field) -> R.string.email_is_required
        field.length < 5 -> R.string.not_valid_email
        field.length > 50 -> R.string.not_valid_email
        !Patterns.EMAIL_ADDRESS.matcher(field).matches() -> R.string.not_valid_email
        else -> null
    }

}