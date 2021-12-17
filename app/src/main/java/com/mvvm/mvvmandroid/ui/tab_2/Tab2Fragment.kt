package com.mvvm.mvvmandroid.ui.tab_2

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.mvvmandroid.R

class Tab2Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab2Fragment()
    }

    private lateinit var viewModel: Tab2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Tab2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}