package com.bootcampteamc.odev.infra

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bootcampteamc.odev.infra.navigation.NavigationObserver




abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    private val navigationObserver = NavigationObserver()


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationObserver.observe(viewModel.navigation, findNavController(), viewLifecycleOwner)

    }
}