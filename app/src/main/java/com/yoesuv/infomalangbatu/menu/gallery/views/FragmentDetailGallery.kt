package com.yoesuv.infomalangbatu.menu.gallery.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentDetailGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel

class FragmentDetailGallery: Fragment() {

    private lateinit var binding: FragmentDetailGalleryBinding
    private lateinit var viewModel: FragmentDetailGalleryViewModel

    private val args: FragmentDetailGalleryArgs by navArgs()
    private var galleryModel: GalleryModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        galleryModel = args.dataDetailGallery
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_gallery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FragmentDetailGalleryViewModel(activity!!.application, galleryModel)
        binding.detailGallery = viewModel
    }

}