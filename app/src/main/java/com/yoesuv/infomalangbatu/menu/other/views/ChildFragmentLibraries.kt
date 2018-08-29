package com.yoesuv.infomalangbatu.menu.other.views

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databinding.ChildFragmentLibrariesBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.LibrariesAdapter
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentLibrariesViewModel

class ChildFragmentLibraries: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentLibraries()
        }
    }

    private lateinit var viewModel: ChildFragmentLibrariesViewModel
    private lateinit var binding: ChildFragmentLibrariesBinding
    private lateinit var adapter: LibrariesAdapter
    private var listLibraries: MutableList<LibraryModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_libraries, container, false)
        viewModel = ChildFragmentLibrariesViewModel()
        binding.libraries = viewModel

        setupRecycler()
        viewModel.setupData(context!!)
        viewModel.listData.observe(this, Observer {
            onListDataChanged(it)
        })

        return binding.root
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListLibraries.layoutManager = layoutManager
        adapter = LibrariesAdapter(context!!, listLibraries)
        binding.recyclerViewListLibraries.adapter = adapter
    }

    private fun onListDataChanged(list: MutableList<LibraryModel>?){
        if (list?.isNotEmpty()!!) {
            Log.d(AppConstants.TAG_DEBUG,"ChildFragmentLibraries # onListDataChanged data size ${list.size}")
            listLibraries.clear()
            listLibraries.addAll(list)
            binding.recyclerViewListLibraries.post {
                adapter.notifyDataSetChanged()
            }
        }
    }

}