package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class ItemGalleryViewModel(galleryModel: GalleryModel): ViewModel() {
    var image: ObservableField<String> = ObservableField(galleryModel.thumbnail!!)
}