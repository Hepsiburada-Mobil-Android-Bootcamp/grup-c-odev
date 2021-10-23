package com.bootcampteamc.odev.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bootcampteamc.odev.ui.home.category.CategoryListFragment

class ViewPagerAdapter(fa: FragmentActivity, private val listOfTitle: List<String>) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = listOfTitle.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CategoryListFragment("Flowers")
            1 -> return CategoryListFragment("Vapes")
            2 -> return CategoryListFragment("Extracts")
            3 -> return CategoryListFragment("Edibles")
            4 -> return CategoryListFragment("Accessories")
        }
        return CategoryListFragment("Flowers")
    }

}
