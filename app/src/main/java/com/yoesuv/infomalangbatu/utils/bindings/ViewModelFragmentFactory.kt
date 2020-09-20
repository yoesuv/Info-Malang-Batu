package com.yoesuv.infomalangbatu.utils.bindings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel

/** REFERENCES
 * https://stackoverflow.com/a/50374088
 */
class ViewModelFragmentFactory(private val params: Any): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == FragmentDetailGalleryViewModel::class.java) {
            return FragmentDetailGalleryViewModel(params as GalleryModel) as T
        }
        return super.create(modelClass)
    }

}