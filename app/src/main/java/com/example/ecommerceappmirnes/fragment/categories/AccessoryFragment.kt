package com.example.ecommerceappmirnes.fragment.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceappmirnes.data.Category
import com.example.ecommerceappmirnes.viewmodel.CategoryViewModel
import com.example.ecommerceappmirnes.viewmodel.factory.BaseCategoryViewModelFactoryFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
@AndroidEntryPoint
class AccessoryFragment: BaseCategoryFragment(){
    @Inject
    lateinit var firestore: FirebaseFirestore
    val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactoryFactory(firestore, Category.Accessory)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest {
                when (it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading->{
                        showOfferLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        offerAdapter.differ.submitList(it.data)
                        hideOfferLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        Snackbar.make(requireView(),it.message.toString(), Snackbar.LENGTH_LONG).show()
                        hideOfferLoading()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProduct.collectLatest {
                when (it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading->{
                        showBestProductsLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        bestProductsAdapter.differ.submitList(it.data)
                        hideBestProductsLoading()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        Snackbar.make(requireView(),it.message.toString(), Snackbar.LENGTH_LONG).show()
                        hideBestProductsLoading()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onBestProductsPagingRequest() {

    }

    override fun onOfferPagingRequest() {

    }
}