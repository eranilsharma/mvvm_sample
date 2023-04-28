package com.sharma.ankitapp.ui.tab_3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.Tab3FragmentBinding


class Tab3Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab3Fragment()
    }

    private lateinit var viewModel: Tab3ViewModel
    private lateinit var binding:Tab3FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(this::binding.isInitialized.not()){
            binding= Tab3FragmentBinding.inflate(inflater)
            viewModel = ViewModelProvider(this).get(Tab3ViewModel::class.java)
            handleViews()
            handleObservers()
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    private fun handleObservers() {

    }

    private fun handleViews() {
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.myBottomNavigationView)
        navBar.visibility=View.GONE
    }



}