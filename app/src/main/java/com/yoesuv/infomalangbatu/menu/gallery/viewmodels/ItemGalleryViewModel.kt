package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.ObservableField
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.views.DetailGalleryActivity
import java.lang.ref.WeakReference

class ItemGalleryViewModel(private val activity: WeakReference<FragmentActivity>, private val galleryModel: GalleryModel): ViewModel() {
    var image: ObservableField<String> = ObservableField(galleryModel.thumbnail!!)

    fun itemClick(view: View){
        Log.d(AppConstants.TAG_DEBUG,"ItemGalleryViewModel # $galleryModel")
        val intent = Intent(activity.get(), DetailGalleryActivity::class.java)
        intent.putExtra(DetailGalleryActivity.EXTRA_DATA_GALLERY, galleryModel)
        activity.get()?.startActivity(intent)

    }

}