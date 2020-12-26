package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class FragmentDetailGalleryViewModel(galleryModel: GalleryModel?) : ViewModel() {

    var imageUrl: ObservableField<String?> = ObservableField(galleryModel?.image)
    var caption: ObservableField<String?> = ObservableField(galleryModel?.caption)

}