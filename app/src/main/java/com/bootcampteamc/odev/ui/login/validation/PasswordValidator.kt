package com.bootcampteamc.odev.ui.login.validation

import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.ui.home.Validator

class PasswordValidator : Validator {
    override fun validate(field: String) = when {
        field.isEmpty() -> R.string.password_is_required
        field.length < 7 -> R.string.password_too_short
        field.length > 40 -> R.string.password_too_long
        !field.contains("""[0-9]""".toRegex()) -> R.string.number
        !field.contains("""[A-Z]""".toRegex()) -> R.string.uppercase
        !field.contains("""[a-z]""".toRegex()) -> R.string.lowercase
        !field.contains("""[~!@#${'$'}%\^&*()\-=+\|\[{\]};:'",<.>/?]""".toRegex()) -> R.string.special
        else -> null
    }

}