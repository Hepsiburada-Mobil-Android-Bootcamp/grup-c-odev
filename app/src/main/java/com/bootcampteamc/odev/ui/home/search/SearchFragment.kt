package com.bootcampteamc.odev.ui.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.infra.BaseFragment
import com.bootcampteamc.odev.infra.BaseViewModel


class SearchFragment(override val viewModel: BaseViewModel) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}