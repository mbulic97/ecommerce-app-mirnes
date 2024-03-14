package com.example.ecommerceappmirnes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeAndORViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
):ViewModel() {
    private val _scannerBarcode = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val scannerBarcode: StateFlow<Resource<List<Product>>> = _scannerBarcode
    fun scanningbarcode(barcode: String){
        viewModelScope.launch {
            _scannerBarcode.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("barcode",barcode).get()
            .addOnSuccessListener { result ->
                val bestProducts = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _scannerBarcode.emit(Resource.Success(bestProducts))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _scannerBarcode.emit(Resource.Error(it.message.toString()))
                }
            }
    }
    /*init {
        viewModelScope.launch {
            _scannerBarcode.emit(Resource.Loading())
        }
        firestore.collection("Products").get()
            .addOnSuccessListener { result ->
                val bestProducts = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _scannerBarcode.emit(Resource.Success(bestProducts))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _scannerBarcode.emit(Resource.Error(it.message.toString()))
                }
            }
    }*/

}