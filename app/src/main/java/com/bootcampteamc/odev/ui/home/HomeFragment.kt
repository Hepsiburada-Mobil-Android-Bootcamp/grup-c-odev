package com.bootcampteamc.odev.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentHomeBinding
import com.bootcampteamc.odev.ui.home.adapter.ViewPagerAdapter
import com.bootcampteamc.odev.ui.home.addproduct.AddProductFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private var categories = arrayListOf<String>()
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager = binding.vpCategories
        tabLayout = binding.tabLayout
        loadCategories()
        setUpViewPagerWithTabLayout()
        addTabLayoutMediator()
        onClickAdd()
        return binding.root
    }


    private fun onClickAdd() {
        //create bottom sheet add fragment when add button clicked on homepage
        binding.buttonDialog.setOnClickListener {
            val dialog = AddProductFragment()
            dialog.show(requireActivity().supportFragmentManager, "addProduct")
        }
    }

    private fun loadCategories() {
        //get product categories from string.xml
        categories = resources.getStringArray(R.array.Categories).toList() as ArrayList<String>

    }

    private fun setUpViewPagerWithTabLayout() {
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ViewPagerAdapter(requireActivity(), categories)
        binding.vpCategories.adapter = pagerAdapter
        addTabLayoutMediator()

    }


    private fun addTabLayoutMediator() {
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = categories[position]
        }.attach()
    }
}