package com.yoesuv.infomalangbatu.utils.bindings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel

/** REFERENCES
 * https://stackoverflow.com/a/50374088
 */
class ViewModelFragmentFactory(private val params: Any): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == FragmentDetailGalleryViewModel::class.java) {
            return FragmentDetailGalleryViewModel(params as GalleryModel) as T
        } else if (modelClass == FragmentDetailListPlaceViewModel::class.java) {
            return FragmentDetailListPlaceViewModel(params as PlaceModel) as T
        }
        return super.create(modelClass)
    }

}