package com.bootcampteamc.odev.ui.home

interface Validator {
    fun validate(field: String): Int?
}