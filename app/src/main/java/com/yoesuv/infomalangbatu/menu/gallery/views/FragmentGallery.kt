package com.yoesuv.infomalangbatu.menu.gallery.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel

class FragmentGallery : Fragment() {

    private var binding: FragmentGalleryBinding? = null
    private val viewModel: FragmentGalleryViewModel by viewModels()
    private var adapter: GalleryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
            binding?.gallery = viewModel
        }
        setupRecycler()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupProperties(context)
        viewModel.listGalleryResponse.observe(viewLifecycleOwner) {
            onListDataChanged(it)
        }
        viewModel.getListGallery()
    }

    private fun setupRecycler() {
        adapter = GalleryAdapter {
            onItemGalleryClick(it)
        }
        binding?.recyclerViewGallery?.adapter = adapter
    }

    private fun onListDataChanged(listData: MutableList<GalleryModel>) {
        if (listData.isNotEmpty()) {
            adapter?.submitList(listData)
        }
    }

    private fun onItemGalleryClick(galleryModel: GalleryModel) {
        val action = FragmentGalleryDirections.actionToGalleryDetail()
        action.dataDetailGallery = galleryModel
        findNavController().navigate(action)
    }

}