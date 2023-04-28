package com.sharma.ankitapp.ui.tab_4

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharma.ankitapp.R

class Tab4Fragment : Fragment() {

    companion object {
        fun newInstance() = Tab4Fragment()
    }

    private lateinit var viewModel: Tab4ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab4_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Tab4ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}