package com.example.bootcamp_group_c.ui.home.addproduct.validation

import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.ui.home.Validator

class NameValidator : Validator {
    override fun validate(field: String) = when {
        field.isEmpty() -> R.string.field_is_required
        field.length < 5 -> R.string.field_is_too_short
        else -> null
    }
}