package com.yoesuv.infomalangbatu.menu.other.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.ChangeLogAdapter
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentChangelogViewModel

class ChildFragmentChangeLog : Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return ChildFragmentChangeLog()
        }
    }

    private var binding: ChildFragmentChangelogBinding? = null
    private val viewModel: ChildFragmentChangelogViewModel by viewModels()
    private lateinit var adapter: ChangeLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_changelog, container, false)
            binding?.changelog = viewModel
        }
        setupRecycler()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupData(requireContext())
        viewModel.listData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupRecycler() {
        adapter = ChangeLogAdapter()
        binding?.recyclerViewChangelog?.adapter = adapter
    }

}