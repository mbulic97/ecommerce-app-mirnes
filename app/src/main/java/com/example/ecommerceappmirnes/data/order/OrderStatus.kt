package com.example.ecommerceappmirnes.data.order

sealed class OrderStatus(val status: String) {
    object Ordered: OrderStatus("Ordered")
    object Canceled: OrderStatus("Canceled")
    object Confirmed: OrderStatus("Confirmed")
    object Shipped: OrderStatus("Shipped")
    object Delivered: OrderStatus("Delivered")
    object Returned: OrderStatus("Returned")

}
fun getOrderStatus(status: String): OrderStatus{
    return when (status){
        "Ordered"-> {
            OrderStatus.Ordered
        }
        "Canceled"-> {
            OrderStatus.Canceled
        }
        "Confirmed"-> {
            OrderStatus.Confirmed
        }
        "Shipped"-> {
            OrderStatus.Shipped
        }
        "Delivered"-> {
            OrderStatus.Delivered
        }
        "Returned"-> {
            OrderStatus.Returned
        }
        else -> OrderStatus.Returned

    }
}