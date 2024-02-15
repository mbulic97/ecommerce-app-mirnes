package com.example.ecommerceappmirnes.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.data.order.Order
import com.example.ecommerceappmirnes.data.order.OrderStatus
import com.example.ecommerceappmirnes.data.order.getOrderStatus
import com.example.ecommerceappmirnes.databinding.OrderItemBinding


class AllOrdersAdapter : Adapter<AllOrdersAdapter.OrdersViewHolder>() {
    inner class OrdersViewHolder(private val binding: OrderItemBinding): ViewHolder(binding.root){
        fun bind(order: Order){
            binding.apply {
                tvOrderId.text =  order.orderId.toString()
                tvOrderDate.text= order.date
                val resource = itemView.resources

                val colorDrawable= when(getOrderStatus(order.orderStatus)){
                    is OrderStatus.Ordered->{
                        ColorDrawable(resource.getColor(R.color.g_orange_yellow))
                    }
                    is OrderStatus.Confirmed->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Delivered->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Shipped->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Canceled->{
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                    is OrderStatus.Returned->{
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                }
                imageOrderState.setImageDrawable(colorDrawable)
            }

        }
    }
    private val diffUtil = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.products == newItem.products
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = differ.currentList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    var onClick: ((Order) -> Unit)?= null
}