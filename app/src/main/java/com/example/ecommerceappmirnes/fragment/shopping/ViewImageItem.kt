package com.example.ecommerceappmirnes.fragment.shopping
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.ViewPager2Images
import com.example.ecommerceappmirnes.data.Product
import com.example.ecommerceappmirnes.databinding.ViewImageItemBinding
import com.example.ecommerceappmirnes.util.Resource
import com.example.ecommerceappmirnes.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ViewImageItem: Fragment() {
    private val args by navArgs<ViewImageItemArgs>()
    private lateinit var binding: ViewImageItemBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    //private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= ViewImageItemBinding.inflate(inflater)
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
        /*lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading->{
                      //  binding.buttonAddToCart.startAnimation()
                    }
                    is Resource.Success->{
                        //binding.buttonAddToCart.revertAnimation()
                       // binding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.black))
                    }
                    is Resource.Error->{
                    //    binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                    else-> Unit
                }
            }
        }*/
        viewPagerAdapter.differ.submitList(product.images)
    }
}
