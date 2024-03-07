package com.example.ecommerceappmirnes.fragment.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.HomeViewpagerAdapter
import com.example.ecommerceappmirnes.databinding.FragmentHomeBinding
import com.example.ecommerceappmirnes.fragment.categories.*
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment(R.layout.fragment_home){
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchbar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        val categoriesFragmnets= arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )
        binding.barcode.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.viewpagerHome.isUserInputEnabled=false
        val viewPager2Adapter= HomeViewpagerAdapter(categoriesFragmnets,childFragmentManager, lifecycle )
        binding.viewpagerHome.adapter=viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){tab, position->
            when(position){
                0-> tab.text="Main"
                1-> tab.text="Chair"
                2-> tab.text="Cupboard"
                3-> tab.text="Table"
                4-> tab.text="Accessory"
                5-> tab.text="Furniture"
            }
        }.attach()
    }
}