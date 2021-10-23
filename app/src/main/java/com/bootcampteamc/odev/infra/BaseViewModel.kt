package com.bootcampteamc.odev.infra

import androidx.lifecycle.ViewModel
import com.bootcampteamc.odev.infra.navigation.Navigation



abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()

}