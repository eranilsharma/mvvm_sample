package com.sharma.ankitapp.ui.register

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.RegisterFragmentBinding
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.DashBoardActivity
import com.sharma.ankitapp.utils.xtnNavigate
import com.sharma.ankitapp.utils.xtnToJson
import com.sharma.ankitapp.utils.xtnToast

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var viewModel: RegisterViewModel

    companion object {
        fun newInstance() = RegisterFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(this::binding.isInitialized.not()){
            binding= RegisterFragmentBinding.inflate(inflater)
            viewModel=ViewModelProvider(this).get(RegisterViewModel::class.java)
            handleViews()
        }
        binding.lifecycleOwner=this.viewLifecycleOwner
        binding.viewModel=viewModel
        return binding.root

    }

    private fun handleViews() {


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleObservers()
    }

    private fun handleObservers() {
        viewModel.loading.observe(this.viewLifecycleOwner) {
            if (it) binding.progressBar.visibility = View.VISIBLE else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.err.observe(this.viewLifecycleOwner){
            requireContext().xtnToast(it)
        }
        viewModel.register.observe(this.viewLifecycleOwner){
            when(it){
                is Data.SUCCESS->{
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