package com.example.ecommerceappmirnes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceappmirnes.data.Category
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel constructor(
    private val firestore: FirebaseFirestore,
    private val category: Category
):ViewModel() {
    private val _offerProducts= MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val offerProducts= _offerProducts.asStateFlow()
    private val _bestProduct= MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProduct= _bestProduct.asStateFlow()
    init {
        fetchBestProducts()
        fetchOfferProducts()
    }
    fun fetchOfferProducts(){
        viewModelScope.launch {
            _offerProducts.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("category",category.category)
            .whereNotEqualTo("offerPercentage",null).get()
            .addOnSuccessListener {
                val product= it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _offerProducts.emit(Resource.Success(product))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _offerProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
    fun fetchBestProducts(){
        viewModelScope.launch {
            _bestProduct.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("category",category.category)
            .whereEqualTo("offerPercentage",null).get()
            .addOnSuccessListener {
                val product= it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProduct.emit(Resource.Success(product))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _bestProduct.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}