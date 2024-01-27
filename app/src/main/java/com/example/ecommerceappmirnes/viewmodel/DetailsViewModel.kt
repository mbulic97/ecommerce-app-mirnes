package com.example.ecommerceappmirnes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceappmirnes.data.CartProduct
import com.example.ecommerceappmirnes.firebase.FirebaseCommon
import com.example.ecommerceappmirnes.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel(){
    private val _addToCart= MutableStateFlow<Resource<CartProduct>>(Resource.Unspecified())
    val addToCart= _addToCart.asStateFlow()
    fun addUpdateProductInCart(cartProduct: CartProduct){
        viewModelScope.launch { _addToCart.emit(Resource.Loading()) }
        firestore.collection("user").document(auth.uid!!).collection("cart")
            .whereEqualTo("product.id", cartProduct.product.id).get().addOnSuccessListener {
                it.documents.let {

                    if(it.isEmpty()) {//Add new product
                        addNewProduct(cartProduct)
                    } else {
                        val product=it.first().toObject(CartProduct::class.java)
                        if(product?.product?.id==cartProduct.product.id
                            &&product?.product?.name==cartProduct.product.name
                            &&product?.product?.category==cartProduct.product.category
                            &&product?.product?.price==cartProduct.product.price
                            &&product?.product?.offerPercentage==cartProduct.product.offerPercentage
                            &&product?.product?.description==cartProduct.product.description
                            &&product?.product?.colors==cartProduct.product.colors
                            &&product?.product?.sizes==cartProduct.product.sizes
                            &&product?.product?.images==cartProduct.product.images
                            &&product?.selectedColor==cartProduct.selectedColor
                            &&product?.selectedSize==cartProduct.selectedSize){//Increase the quantity kao raste
                            val documentId=it.first().id
                            increaseQuantity(documentId,cartProduct)
                        }else{// Add new product
                            addNewProduct(cartProduct)
                        }
                    }
                }

            }.addOnFailureListener {//u slucaju neuspjeha
                viewModelScope.launch { _addToCart.emit(Resource.Error(it.message.toString())) }
            }
    }
    private fun addNewProduct(cartProduct: CartProduct){
        firebaseCommon.addProductToCart(cartProduct){addedProduct ,e->
            viewModelScope.launch {
                if(e==null)
                    _addToCart.emit(Resource.Success(addedProduct!!))
                else
                    _addToCart.emit(Resource.Error(e.message.toString()))
            }
        }
    }
    private fun increaseQuantity(documentId: String, cartProduct: CartProduct){
        firebaseCommon.increaseQuantity(documentId){_,e->
            viewModelScope.launch {
                if(e==null)
                    _addToCart.emit(Resource.Success(cartProduct))
                else
                    _addToCart.emit(Resource.Error(e.message.toString()))
            }

        }
    }
}