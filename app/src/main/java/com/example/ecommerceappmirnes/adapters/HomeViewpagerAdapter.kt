package com.example.ecommerceappmirnes.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle

class HomeViewpagerAdapter (
    private val fragment: List<Fragment>,
            fm: FragmentManager,
    lifecycle : androidx.lifecycle.Lifecycle
):FragmentStateAdapter(fm,lifecycle){
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}