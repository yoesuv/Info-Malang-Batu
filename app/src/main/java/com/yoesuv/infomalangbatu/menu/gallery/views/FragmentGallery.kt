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
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel
import com.yoesuv.infomalangbatu.utils.ViewModelFactory

class FragmentGallery : Fragment() {

    private var binding: FragmentGalleryBinding? = null
    private lateinit var adapter: GalleryAdapter
    private val viewModel: FragmentGalleryViewModel by viewModels {
        ViewModelFactory(AppDbRepository(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
            binding?.gallery = viewModel
        }
        binding?.lifecycleOwner = viewLifecycleOwner
        setupRecycler()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGallery()
    }

    private fun setupRecycler() {
        adapter = GalleryAdapter { galleryModel ->
            onItemGalleryClick(galleryModel)
        }
        binding?.recyclerViewGallery?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        binding?.recyclerViewGallery?.adapter = adapter
    }

    private fun loadGallery() {
        viewModel.getListGallery()?.observe(viewLifecycleOwner) { listGallery ->
            onListDataChanged(listGallery)
        }
    }

    private fun onListDataChanged(listData: List<GalleryModel>) {
        if (listData.isNotEmpty()) {
            adapter.submitList(listData)
        }
    }

    private fun onItemGalleryClick(galleryModel: GalleryModel) {
        val action = FragmentGalleryDirections.actionToGalleryDetail()
        action.dataDetailGallery = galleryModel
        findNavController().navigate(action)
    }

}