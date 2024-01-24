package com.example.ecommerceappmirnes.fragment.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.activities.ShoppingActivity
import com.example.ecommerceappmirnes.adapters.ColorsAdapter
import com.example.ecommerceappmirnes.adapters.SizesAdapter
import com.example.ecommerceappmirnes.adapters.ViewPager2Images
import com.example.ecommerceappmirnes.databinding.FragmentProductDetailsBinding
import com.example.ecommerceappmirnes.util.hideBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment: Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy {ViewPager2Images()}
    private val sizesAdapter by lazy {SizesAdapter()}
    private val colorsAdapter by lazy {ColorsAdapter()}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding= FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product= args.product

        setupSizesRv()
        setupColorsRv()
        setupViewpager()
        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.apply {
            tvProductName.text=product.name
            tvProductPrice.text="$ ${product.price}"
            tvProductDescription.text=product.description
            if(product.colors.isNullOrEmpty())
                tvProductColors.visibility=View.INVISIBLE
            if(product.sizes.isNullOrEmpty())
                tvProductSize.visibility=View.INVISIBLE
        }
        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupViewpager() {
        binding.apply {
            viewPagerProductImages.adapter=viewPagerAdapter
        }
    }

    private fun setupColorsRv() {
        binding.rvColors.apply {
            adapter=colorsAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setupSizesRv() {
        binding.rvSize.apply {
            adapter=sizesAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
}