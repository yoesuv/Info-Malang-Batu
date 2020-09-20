package com.yoesuv.infomalangbatu.menu.gallery.views

import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel

class FragmentGallery: Fragment() {

    private lateinit var viewModel: FragmentGalleryViewModel
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentGalleryViewModel::class.java)
        binding.gallery = viewModel

        setupRecycler()

        viewModel.setupProperties(context)
        viewModel.listGalleryResponse.observe(viewLifecycleOwner, {
            onListDataChanged(it)
        })
        viewModel.getListGallery()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        activity?.recreate()
    }

    private fun setupRecycler(){
        adapter = GalleryAdapter {
            onItemGalleryClick(it)
        }
        binding.recyclerViewGallery.adapter = adapter
    }

    private fun onListDataChanged(listData: MutableList<GalleryModel>?){
        if (listData?.isNotEmpty()!!){
            adapter.submitList(listData)
        }
    }

    private fun onItemGalleryClick(galleryModel: GalleryModel){
        val action = FragmentGalleryDirections.actionToGalleryDetail()
        action.dataDetailGallery = galleryModel
        findNavController().navigate(action)
    }

}