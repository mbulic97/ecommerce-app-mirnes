package com.example.ecommerceappmirnes.data.order

import com.example.ecommerceappmirnes.data.Address
import com.example.ecommerceappmirnes.data.CartProduct

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val products: List<CartProduct>,
    val address: Address
)