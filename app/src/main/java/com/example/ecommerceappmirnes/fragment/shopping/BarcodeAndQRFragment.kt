package com.example.ecommerceappmirnes.fragment.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.adapters.BarcodeAndQRAdapter
import com.example.ecommerceappmirnes.databinding.FragmentBarcodeAndQrBinding
import com.example.ecommerceappmirnes.util.hideBottomNavigationView
import com.example.ecommerceappmirnes.viewmodel.BarcodeAndORViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
private val TAG="BarcodeAndQRFragment"

@AndroidEntryPoint
class BarcodeAndQRFragment(): Fragment(R.layout.fragment_barcode_and_qr) {
    private lateinit var binding: FragmentBarcodeAndQrBinding
    private val viewModel by viewModels<BarcodeAndORViewModel>()
    private lateinit var BarcodeAndQEAdapter: BarcodeAndQRAdapter
    private var barcode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        arguments?.let {
            barcode = it.getString("barcode")
        }
        binding=FragmentBarcodeAndQrBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        BarcodeAndQEAdapter= BarcodeAndQRAdapter()
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(requireContext(),1,GridLayoutManager.VERTICAL,false)
            adapter=BarcodeAndQEAdapter
        }

        viewModel.scanningbarcode(barcode.toString())
        lifecycleScope.launchWhenStarted {
            viewModel.scannerBarcode.collectLatest {
                when(it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading ->{
                        showLoading()
                        hideEmptyBarcode()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        if(it.data!!.isEmpty()){
                            showEmptyBarcode()
                            hideLoading()
                        }
                        else{
                            hideEmptyBarcode()
                            BarcodeAndQEAdapter.differ.submitList(it.data)
                            hideLoading()
                        }

                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        hideLoading()
                        hideEmptyBarcode()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun hideLoading() {
        binding.BarcodeAndQRProgressbar.visibility=View.GONE
    }
    private fun showLoading() {
        binding.BarcodeAndQRProgressbar.visibility=View.VISIBLE
    }
    private fun hideEmptyBarcode() {
        binding.apply {
            layoutBarcodeEmpty.visibility= View.GONE
        }
    }

    private fun showEmptyBarcode() {
        binding.apply {
            layoutBarcodeEmpty.visibility= View.VISIBLE
        }
    }

}