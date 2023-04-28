package com.sharma.ankitapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavAction
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.HomeFragmentBinding
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.home.model.SubjectList
import com.sharma.ankitapp.utils.xtnToJson
import com.sharma.ankitapp.utils.xtnToast

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized.not()) {
            binding = HomeFragmentBinding.inflate(inflater)
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            handleViews()
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
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
        viewModel.err.observe(this.viewLifecycleOwner) {
            requireContext().xtnToast(it)
        }
        viewModel.subjectList.observe(this.viewLifecycleOwner) {
            when (it) {
                is Data.SUCCESS -> {
                    val data: SubjectList =
                        Gson().fromJson(it.data.xtnToJson(), SubjectList::class.java)
                    binding.subjectList.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = SubjectListAdapter(requireContext(), data, OnClickListener {
                            findNavController().navigate(R.id.tab3Fragment)
                        })
                    }
                }
                is Data.ERROR -> {
                    it.throwable.message?.let { it1 ->
                        requireContext().xtnToast(it1)
                    }
                }
            }
        }

    }
}