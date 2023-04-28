package com.sharma.ankitapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sharma.ankitapp.R
import com.sharma.ankitapp.ui.DashBoardActivity
import com.sharma.ankitapp.utils.xtnNavigate
import com.sharma.ankitapp.utils.xtnRunDelayed


class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(SplashViewModel::class.java)
        handleObservers()
    }

    private fun handleObservers() {
        viewModel.isLogin.observe(this.viewLifecycleOwner){
            if(it){
                requireContext().xtnNavigate<DashBoardActivity>()
                requireActivity().finishAffinity()
            }
            else{
                findNavController().navigate(R.id.action_global_loginFragment)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}