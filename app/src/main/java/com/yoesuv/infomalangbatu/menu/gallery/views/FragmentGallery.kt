package com.yoesuv.infomalangbatu.menu.gallery.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel

class FragmentGallery: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return FragmentGallery()
        }
    }

    private lateinit var viewModel:FragmentGalleryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        viewModel = ViewModelProviders.of(this).get(FragmentGalleryViewModel::class.java)
        binding.gallery = viewModel

        viewModel.getGallery()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

}