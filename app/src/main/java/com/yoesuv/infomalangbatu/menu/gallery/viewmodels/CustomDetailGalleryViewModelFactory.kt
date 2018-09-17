package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class CustomDetailGalleryViewModelFactory(private val galleryModel: GalleryModel?, private val application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailGalleryViewModel(galleryModel, application) as T
    }

}