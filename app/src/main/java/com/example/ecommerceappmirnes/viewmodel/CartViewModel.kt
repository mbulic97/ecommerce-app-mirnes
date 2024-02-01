package com.example.ecommerceappmirnes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.ecommerceappmirnes.data.CartProduct
import com.example.ecommerceappmirnes.firebase.FirebaseCommon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel() {
    private val _cartProducts= MutableStateFlow<com.example.ecommerceappmirnes.util.Resource<List<CartProduct>>>(com.example.ecommerceappmirnes.util.Resource.Unspecified())
    val cartProducts= _cartProducts.asStateFlow()
    private var cartProductDocuments= emptyList<DocumentSnapshot>()//snimak dokument
    init {
        getCartProducts()
    }
    private fun getCartProducts(){
        viewModelScope.launch { _cartProducts.emit(com.example.ecommerceappmirnes.util.Resource.Loading()) }
        firestore.collection("user").document(auth.uid!!).collection("cart")
            .addSnapshotListener { value, error ->
                if(error!=null|| value==null){
                    viewModelScope.launch { _cartProducts.emit(com.example.ecommerceappmirnes.util.Resource.Error(error?.message.toString())) }
                }else{
                    cartProductDocuments=value.documents
                    val cartProduct= value.toObjects(CartProduct::class.java)
                    viewModelScope.launch { _cartProducts.emit(com.example.ecommerceappmirnes.util.Resource.Success(cartProduct))}
                }
            }
    }
    fun changeQuantity(cartProduct: CartProduct,quantityChanging: FirebaseCommon.QuantityChanging){
        val index= cartProducts.value.data?.indexOf(cartProduct)
        if(index!=null&&index!=-1) {
            val documentId = cartProductDocuments[index].id
            when(quantityChanging){
                FirebaseCommon.QuantityChanging.INCREASE ->{//kolicina tocke se poveceava
                    increaseQuantity(documentId)
                }
                FirebaseCommon.QuantityChanging.DECREASE->{//smanjimo kolicinu
                    decreaseQuantity(documentId)
                }
            }
        }

    }

    private fun decreaseQuantity(documentId: String) {
        firebaseCommon.decreaseQuantity(documentId){
            result, exception ->
            if(exception!=null)
                viewModelScope.launch { _cartProducts.emit(com.example.ecommerceappmirnes.util.Resource.Error(exception.message.toString())) }
        }
    }

    private fun increaseQuantity(documentId: String){
        firebaseCommon.increaseQuantity(documentId){
                result, exception ->
            if(exception!=null)
                viewModelScope.launch { _cartProducts.emit(com.example.ecommerceappmirnes.util.Resource.Error(exception.message.toString())) }
        }
    }
}