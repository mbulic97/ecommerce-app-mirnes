package com.example.ecommerceappmirnes.fragment.shopping
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerceappmirnes.adapters.ViewPager2Images
import com.example.ecommerceappmirnes.databinding.ViewImageItemFullBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewImageItem: Fragment() {
    private val args by navArgs<ViewImageItemArgs>()
    private lateinit var binding: ViewImageItemFullBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= ViewImageItemFullBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product=args.product
        binding.apply {
            viewPagerProductImages.adapter=viewPagerAdapter
        }
        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }
        viewPagerAdapter.differ.submitList(product.images)
    }
}
