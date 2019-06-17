package com.yoesuv.infomalangbatu.menu.other.views

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentLibrariesBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.LibrariesAdapter
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
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
            listLibraries.clear()
            listLibraries.addAll(list)
            binding.recyclerViewListLibraries.post {
                adapter.notifyDataSetChanged()
            }
        }
    }

}