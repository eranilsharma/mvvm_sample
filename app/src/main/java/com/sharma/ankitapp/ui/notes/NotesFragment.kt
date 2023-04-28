package com.sharma.ankitapp.ui.notes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.NotesFragmentBinding
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.home.OnClickListener
import com.sharma.ankitapp.ui.home.SubjectListAdapter
import com.sharma.ankitapp.ui.home.model.SubjectList
import com.sharma.ankitapp.utils.xtnToJson
import com.sharma.ankitapp.utils.xtnToast

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: NotesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized.not()) {
            binding = NotesFragmentBinding.inflate(inflater)
            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
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
                        adapter = SubjectListAdapter(requireContext(), data, onClickListener = OnClickListener {

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