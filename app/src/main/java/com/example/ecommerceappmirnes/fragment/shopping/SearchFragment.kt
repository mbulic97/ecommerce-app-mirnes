package com.example.ecommerceappmirnes.fragment.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if(query!=null){
            val filteredList= kotlin.collections.ArrayList<Product>()
            for(i in searchAdapter.differ.currentList) {
                if (i.name.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if(filteredList.isNotEmpty()){
                val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Success(filteredList))
                val specialProduct: StateFlow<Resource<List<Product>>> = _specialProducts
                lifecycleScope.launchWhenStarted {
                    specialProduct.collectLatest {
                        when (it) {
                            is com.example.ecommerceappmirnes.util.Resource.Success -> {
                                searchAdapter.differ.submitList(it.data)
                            }
                            else -> Unit
                        }
                    }
                }
                searchAdapter.dsad()
            }

        }
    }
}
