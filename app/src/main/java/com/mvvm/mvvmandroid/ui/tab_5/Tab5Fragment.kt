package com.mvvm.mvvmandroid.ui.tab_5

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.mvvmandroid.R

class Tab5Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab5Fragment()
    }

    private lateinit var viewModel: Tab5ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab5_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Tab5ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}