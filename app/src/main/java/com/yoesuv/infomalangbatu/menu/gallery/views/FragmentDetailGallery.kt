package com.yoesuv.infomalangbatu.menu.gallery.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yoesuv.infomalangbatu.databinding.FragmentDetailGalleryBinding
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel
import com.yoesuv.infomalangbatu.utils.bindings.ViewModelFragmentFactory

class FragmentDetailGallery : Fragment() {

    private var binding: FragmentDetailGalleryBinding? = null
    private val viewModel: FragmentDetailGalleryViewModel by viewModels { ViewModelFragmentFactory(galleryModel as Any) }

    private val args: FragmentDetailGalleryArgs by navArgs()
    private var galleryModel: GalleryModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryModel = args.dataDetailGallery
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentDetailGalleryBinding.inflate(inflater, container, false)
            binding?.detailGallery = viewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideNavigation(true)
        onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideNavigation(false)
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }

}