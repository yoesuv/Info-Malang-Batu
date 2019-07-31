package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class FragmentDetailGalleryViewModel(application: Application, galleryModel: GalleryModel?) : AndroidViewModel(application) {

    var imageUrl: ObservableField<String?> = ObservableField(galleryModel?.image)
    var caption: ObservableField<String?> = ObservableField(galleryModel?.caption)

}