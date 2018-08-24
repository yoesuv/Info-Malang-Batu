package com.yoesuv.infomalangbatu.menu.other.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.ChangeLogAdapter
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentChangelogViewModel

class ChildFragmentChangeLog: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentChangeLog()
        }
    }

    private lateinit var binding: ChildFragmentChangelogBinding
    private lateinit var viewModel: ChildFragmentChangelogViewModel
    private lateinit var adapter:ChangeLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_changelog, container, false)
        viewModel = ViewModelProviders.of(this).get(ChildFragmentChangelogViewModel::class.java)
        binding.changelog = viewModel

        setupRecycler()

        viewModel.setupData(context!!)
        viewModel.listData.observe(this, Observer {

        })

        return binding.root
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewChangelog.layoutManager = layoutManager
        adapter = ChangeLogAdapter(activity!!)
        binding.recyclerViewChangelog.adapter = adapter

    }

}