package com.example.ecommerceappmirnes.fragment.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.SearchAdapter
import com.example.ecommerceappmirnes.adapters.SpecialProductsAdapter
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest


import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceappmirnes.adapters.BestProductAdapter
import com.example.ecommerceappmirnes.data.User
import com.example.ecommerceappmirnes.databinding.FragmentSearchBinding
import com.example.ecommerceappmirnes.databinding.SearchItemBinding
import com.example.ecommerceappmirnes.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint

class SearchFragment : Fragment(R.layout.search_item) {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSearchBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter= SearchAdapter()
        binding.recyclerView.apply {
            layoutManager= GridLayoutManager(requireContext(),1, GridLayoutManager.VERTICAL,false)
            adapter=searchAdapter
        }
        searchAdapter.onClick={
            val b=Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_searchFragment_to_productDetailsFragment,b)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.specialProduct.collectLatest {
                when (it) {
                    is com.example.ecommerceappmirnes.util.Resource.Success -> {
                        searchAdapter.differ.submitList(it.data)
                    }
                    else -> Unit
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = viewModel.specialProduct.value.data?.filter {product ->
                    product.name.contains(newText, ignoreCase = true)
                    }
                    searchAdapter.differ.submitList(filteredList)

                }
                return true
            }
        })
    }


}
