package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class ItemGalleryViewModel(
    galleryModel: GalleryModel,
) : ViewModel() {
    var image: ObservableField<String> = ObservableField(galleryModel.thumbnail!!)
}
