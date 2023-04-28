package com.sharma.ankitapp.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.utils.Utils
import com.google.firebase.auth.FirebaseUser
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.LoginFragmentBinding
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.DashBoardActivity
import com.sharma.ankitapp.utils.xtnNavigate
import com.sharma.ankitapp.utils.xtnToast

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       if(this::binding.isInitialized.not()){
        binding= LoginFragmentBinding.inflate(inflater)
        viewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        handleViews()
        }
        binding.lifecycleOwner=this.viewLifecycleOwner
        binding.viewModel=viewModel
        return binding.root

    }

    private fun handleViews() {
        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleObservers()

    }

    private fun handleObservers() {
        viewModel.loading.observe(this.viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.err.observe(this.viewLifecycleOwner){
            requireContext().xtnToast(it)
        }
        viewModel.loginFlow.observe(this.viewLifecycleOwner){
            when(it){
                is Data.SUCCESS ->{
                    requireContext().xtnNavigate<DashBoardActivity>()
                    requireActivity().finishAffinity()
                }
                is Data.ERROR -> {
                    it.throwable.message?.let { it1 -> requireContext().xtnToast(it1) }
                }

            }
        }

    }


}