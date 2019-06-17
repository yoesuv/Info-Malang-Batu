package com.yoesuv.infomalangbatu.menu.other.views

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.ChangeLogAdapter
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentChangelogViewModel

class ChildFragmentChangeLog: Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return ChildFragmentChangeLog()
        }
    }

    private lateinit var binding: ChildFragmentChangelogBinding
    private lateinit var viewModel: ChildFragmentChangelogViewModel
    private lateinit var adapter:ChangeLogAdapter
    private var listChangeLog: MutableList<ChangeLogModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_changelog, container, false)
        viewModel = ViewModelProviders.of(this).get(ChildFragmentChangelogViewModel::class.java)
        binding.changelog = viewModel

        setupRecycler()

        viewModel.setupData(context!!)
        viewModel.listData.observe(this, Observer {
            onListDataChange(it)
        })

        return binding.root
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewChangelog.layoutManager = layoutManager
        adapter = ChangeLogAdapter(activity!!, listChangeLog)
        binding.recyclerViewChangelog.adapter = adapter
    }

    private fun onListDataChange(list: MutableList<ChangeLogModel>?){
        if (list?.isNotEmpty()!!){
            listChangeLog.clear()
            listChangeLog.addAll(list)
            binding.recyclerViewChangelog.post {
                adapter.notifyDataSetChanged()
            }
        }
    }

}