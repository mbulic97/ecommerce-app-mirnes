package com.example.ecommerceappmirnes.fragment.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.engine.Resource
import com.example.ecommerceappmirnes.R
import com.example.ecommerceappmirnes.data.User
import com.example.ecommerceappmirnes.databinding.FragmentRegisterBinding
import com.example.ecommerceappmirnes.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
private val TAG="RegisterFragment"
@AndroidEntryPoint
class RegisterFragment: Fragment() {
 private lateinit var binding: FragmentRegisterBinding
 private val viewModel by viewModels <RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user=User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )
                val password=edPasswordRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user,password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is com.example.ecommerceappmirnes.util.Resource.Loading->{
                        binding.buttonRegisterRegister.startAnimation()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Success->{
                        Log.d("test",it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    is com.example.ecommerceappmirnes.util.Resource.Error->{
                        Log.e(TAG,it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()

                    }
                    else ->Unit
                }
            }
        }
    }
}