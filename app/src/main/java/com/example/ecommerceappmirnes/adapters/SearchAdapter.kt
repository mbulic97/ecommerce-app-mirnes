package com.example.ecommerceappmirnes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.databinding.SearchItemBinding

class SearchAdapter(): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(logoIv)
                titleTv.text= product.name
                SearchPrice.text="$ ${product.price}"


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }
    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product= differ.currentList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
   }
    override fun getItemCount(): Int {
        if(differ.currentList.size>3){//ako preko 3 list mozda stopa telefon i baterija vruca
            return 3
        }
        else {
            return differ.currentList.size
        }

    }
    var onClick: ((Product)-> Unit)?=null

}