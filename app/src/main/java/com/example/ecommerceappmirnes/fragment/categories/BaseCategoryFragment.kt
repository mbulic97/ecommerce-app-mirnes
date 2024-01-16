package com.example.ecommerceappmirnes.fragment.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.BestProductAdapter
import com.example.ecommerceappmirnes.databinding.FragmentBaseCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

open class BaseCategoryFragment:Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductAdapter by lazy { BestProductAdapter() }
    protected val bestProductsAdapter: BestProductAdapter by lazy { BestProductAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOfferRv()
        setupBestProductsRv()
        binding.rvBestProducts.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)&&dx!=0){
                    onOfferPagingRequest()
                }
            }
        })

    }
    fun showOfferLoading(){
        binding.offerProductsProgressBar.visibility=View.VISIBLE
    }
    fun hideOfferLoading(){
        binding.offerProductsProgressBar.visibility=View.GONE
    }
    fun showBestProductsLoading(){
        binding.bestProductsProgressBar.visibility=View.VISIBLE
    }
    fun hideBestProductsLoading(){
        binding.bestProductsProgressBar.visibility=View.GONE
    }
    open fun onOfferPagingRequest(){

    }
    open fun onBestProductsPagingRequest(){

    }
    private fun setupBestProductsRv() {

        binding.rvBestProducts.apply {
            layoutManager=
                GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter=bestProductsAdapter
        }
    }

    private fun setupOfferRv() {

        binding.rvOfferProducts.apply {
            layoutManager=
                LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=offerAdapter
        }
    }
}
