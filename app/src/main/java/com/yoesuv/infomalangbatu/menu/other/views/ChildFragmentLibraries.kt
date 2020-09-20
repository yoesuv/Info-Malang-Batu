package com.yoesuv.infomalangbatu.menu.other.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.databinding.ChildFragmentLibrariesBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.LibrariesAdapter
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentLibrariesViewModel

class ChildFragmentLibraries: Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return ChildFragmentLibraries()
        }
    }

    private lateinit var viewModel: ChildFragmentLibrariesViewModel
    private lateinit var binding: ChildFragmentLibrariesBinding
    private lateinit var adapter: LibrariesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChildFragmentLibrariesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ChildFragmentLibrariesViewModel::class.java)
        binding.libraries = viewModel

        setupRecycler()
        viewModel.setupData(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun setupRecycler(){
        adapter = LibrariesAdapter()
        binding.recyclerViewListLibraries.adapter = adapter
    }

}