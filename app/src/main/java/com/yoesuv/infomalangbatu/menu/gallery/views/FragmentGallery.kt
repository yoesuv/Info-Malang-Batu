package com.yoesuv.infomalangbatu.menu.gallery.views

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel

class FragmentGallery: androidx.fragment.app.Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return FragmentGallery()
        }
    }

    private lateinit var viewModel: FragmentGalleryViewModel
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        viewModel = ViewModelProviders.of(this).get(FragmentGalleryViewModel::class.java)
        binding.gallery = viewModel

        setupRecycler()
        setupSwipeRefresh()

        viewModel.getGallery()
        viewModel.listGalleryResponse.observe(this, Observer {
            onListDataChanged(it)
        })

        return binding.root
    }

    private fun setupRecycler(){
        val layoutManager = GridLayoutManager(context, 3)
        binding.recyclerViewGallery.layoutManager = layoutManager
        adapter = GalleryAdapter()
        binding.recyclerViewGallery.adapter = adapter
    }

    private fun setupSwipeRefresh(){
        binding.swipeRefreshLayoutGallery.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        binding.swipeRefreshLayoutGallery.setOnRefreshListener {
            binding.swipeRefreshLayoutGallery.isRefreshing = false
            viewModel.getGallery()
        }
    }

    private fun onListDataChanged(listData: MutableList<GalleryModel>?){
        if (listData?.isNotEmpty()!!){
            adapter.submitList(listData)
        }
    }

}