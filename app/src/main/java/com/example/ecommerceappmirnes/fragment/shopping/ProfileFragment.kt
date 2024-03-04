package com.example.ecommerceappmirnes.fragment.shopping

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
//import com.example.ecommerceappmirnes.BuildConfig
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.activities.LoginRegisterActivity
import com.example.ecommerceappmirnes.databinding.FragmentProfileBinding
import com.example.ecommerceappmirnes.util.Resource
import com.example.ecommerceappmirnes.util.showBottomNavigationView
import com.example.ecommerceappmirnes.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
@AndroidEntryPoint
class ProfileFragment: Fragment(){
    private lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }
        binding.linearAllOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_ordersFragment)
        }
        binding.linearBilling.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBillingFragment(0f,
                emptyArray()
            )
            findNavController().navigate(action)
        }
        binding.linearLogOut.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireActivity(),LoginRegisterActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        //binding.tvVersion.text = "Version ${BuildConfig.VERSION_CODE}"

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it){
                    is Resource.Loading ->{
                        binding.progressbarSettings.visibility=View.VISIBLE
                    }
                    is Resource.Success ->{
                        binding.progressbarSettings.visibility= View.GONE
                        Glide.with(requireView()).load(it.data!!.imagePath).error(ColorDrawable(
                            android.graphics.Color.BLACK)).into(binding.imageUser)
                        binding.tvUserName.text="${it.data.firstName} ${it.data.lastName}"
                    }
                    is Resource.Error ->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        binding.progressbarSettings.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}