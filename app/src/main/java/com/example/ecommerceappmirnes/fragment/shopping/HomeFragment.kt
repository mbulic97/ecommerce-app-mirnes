package com.example.ecommerceappmirnes.fragment.shopping

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.HomeViewpagerAdapter
import com.example.ecommerceappmirnes.databinding.FragmentHomeBinding
import com.example.ecommerceappmirnes.fragment.categories.*
import com.google.android.material.tabs.TabLayoutMediator
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
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
        binding.barcode.setOnClickListener {
            val scanner= IntentIntegrator.forSupportFragment(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            scanner.setCameraId(0)
            scanner.setBeepEnabled(false)
            scanner.setBarcodeImageEnabled(false)
            scanner.initiateScan()
        }
        val categoriesFragmnets= arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (result != null){
            if(result.contents==null){
                Toast.makeText(context,"Cancelled", Toast.LENGTH_LONG).show()
            }
            else{
                val bundle = Bundle().apply {
                    putString("barcode",result.contents)
                }

                findNavController().navigate(R.id.action_homeFragment_to_barcodeAndQRFragment,bundle)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}