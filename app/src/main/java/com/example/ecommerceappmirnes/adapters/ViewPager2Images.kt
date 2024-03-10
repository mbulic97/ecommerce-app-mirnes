package com.example.ecommerceappmirnes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.ecommerceappmirnes.databinding.ViewpagerImageItemBinding
import com.example.ecommerceappmirnes.fragment.shopping.ProductDetailsFragmentArgs
import com.example.ecommerceappmirnes.fragment.shopping.ViewImageItem

class ViewPager2Images :RecyclerView.Adapter<ViewPager2Images.ViewPager2ImagesHolder>() {
    inner class ViewPager2ImagesHolder(val binding: ViewpagerImageItemBinding): ViewHolder(binding.root){
        fun bind(imagePath: String){
            Glide.with(itemView).load(imagePath).into(binding.imageProductDetails)
        }
    }
    private val diffCallback= object: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ImagesHolder {
        return ViewPager2ImagesHolder(
            ViewpagerImageItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPager2ImagesHolder, position: Int) {
        val image=differ.currentList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}