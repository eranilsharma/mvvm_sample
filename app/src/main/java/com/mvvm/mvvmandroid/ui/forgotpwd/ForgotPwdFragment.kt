package com.mvvm.mvvmandroid.ui.forgotpwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mvvm.mvvmandroid.R

class ForgotPwdFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPwdFragment()
    }

    private lateinit var viewModel: ForgotPwdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forgot_pwd_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgotPwdViewModel::class.java)
        // TODO: Use the ViewModel

        
    }

}