package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class ItemGalleryViewModel(private val galleryModel: GalleryModel): ViewModel() {
    var image: ObservableField<String> = ObservableField(galleryModel.thumbnail!!)
}