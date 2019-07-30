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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel

class FragmentGallery: androidx.fragment.app.Fragment() {

    private lateinit var viewModel: FragmentGalleryViewModel
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentGalleryViewModel::class.java)
        binding.gallery = viewModel

        setupRecycler()
        setupSwipeRefresh()

        viewModel.getGallery()
        viewModel.listGalleryResponse.observe(this, Observer {
            onListDataChanged(it)
        })
    }

    private fun setupRecycler(){
        val layoutManager = GridLayoutManager(context, 3)
        binding.recyclerViewGallery.layoutManager = layoutManager
        adapter = GalleryAdapter {
            onItemGalleryClick(it)
        }
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

    private fun onItemGalleryClick(galleryModel: GalleryModel){
        val action = FragmentGalleryDirections.actionNotificationToGalleryDetail()
        action.dataDetailGallery = galleryModel
        findNavController().navigate(action)
    }

}