package com.example.ecommerceappmirnes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.databinding.BarcodeAndQrItemBinding
import com.example.ecommerceappmirnes.databinding.FragmentBarcodeAndQrBinding
import com.example.ecommerceappmirnes.databinding.ProductRvItemBinding
import com.example.ecommerceappmirnes.fragment.shopping.BarcodeAndQRFragment

class BarcodeAndQRAdapter(): RecyclerView.Adapter<BarcodeAndQRAdapter.BarcodeViewHolder>() {
    inner class BarcodeViewHolder(private var binding: BarcodeAndQrItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(logoIv)
                titleTv.text= product.name
                BarcodePrice.text="$ ${product.price}"
            }
        }
    }
    private val diffCallBack=object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    val differ= AsyncListDiffer(this,diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeViewHolder {
        return BarcodeViewHolder(
            BarcodeAndQrItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: BarcodeViewHolder, position: Int) {
        val product=differ.currentList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    var onClick: ((Product)-> Unit)?=null

}