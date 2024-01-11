package com.example.ecommerceappmirnes.fragment.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.BestDealsAdapter
import com.example.ecommerceappmirnes.adapters.BestProductAdapter
import com.example.ecommerceappmirnes.adapters.SpecialProductsAdapter
import com.example.ecommerceappmirnes.databinding.FragmentMainCategoryBinding
import com.example.ecommerceappmirnes.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
private val TAG="MainCategoryFragment"
@AndroidEntryPoint
class MainCategoryFragment :Fragment(R.layout.fragment_main_category){
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var bestProductsAdapter: BestProductAdapter
    private val viewModel by viewModels<MainCategoryViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpecialProductsRv()
        setupBestDealsRv()
        setupBestProductsRv()
        lifecycleScope.launchWhenStarted {
            viewModel.specialProduct.collectLatest {
                when(it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading ->{
                        showLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        specialProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when(it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading ->{
                        showLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when(it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading ->{
                        showLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        bestProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun setupBestProductsRv() {
        bestProductsAdapter= BestProductAdapter()
        binding.rvBestProducts.apply {
            layoutManager= GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter=bestProductsAdapter
        }
    }

    private fun setupBestDealsRv() {
        bestDealsAdapter= BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=bestDealsAdapter
        }
    }

    private fun hideLoading() {
        binding.mainCategoryProgressbar.visibility=View.GONE
    }

    private fun showLoading() {
        binding.mainCategoryProgressbar.visibility=View.VISIBLE
    }

    private fun setupSpecialProductsRv() {
        specialProductsAdapter= SpecialProductsAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=specialProductsAdapter
        }
    }
}