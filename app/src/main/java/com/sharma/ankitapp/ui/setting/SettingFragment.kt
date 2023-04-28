package com.sharma.ankitapp.ui.setting

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.SettingFragmentBinding
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.DashBoardActivity
import com.sharma.ankitapp.ui.PreLoginActivity
import com.sharma.ankitapp.utils.xtnNavigate
import com.sharma.ankitapp.utils.xtnToast
import kotlinx.android.synthetic.main.fragment_login.*

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }
    private lateinit var binding: SettingFragmentBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(this::binding.isInitialized.not()){
            binding= SettingFragmentBinding.inflate(inflater)
            viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
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
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.err.observe(this.viewLifecycleOwner){
            requireContext().xtnToast(it)
        }
        viewModel.userDetails.observe(this.viewLifecycleOwner){
            when(it){
                is Data.SUCCESS ->{
                    Log.i("TAG", "handleObservers: ${it.data}")
                    viewModel.email.postValue(it.data.email)
                    viewModel.name.postValue(it.data.displayName)
                }
                is Data.ERROR -> {
                    it.throwable.message?.let { it1 -> requireContext().xtnToast(it1) }
                }

            }

        }
        viewModel.logout.observe(this.viewLifecycleOwner){
            if(it){
                requireContext().xtnNavigate<PreLoginActivity>()
                requireActivity().finishAffinity()
            }

        }
    }

}