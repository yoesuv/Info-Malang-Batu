package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.databases.AppDbRepository

class FragmentGalleryViewModel : ViewModel() {

    private var appDbRepository: AppDbRepository? = null

    fun setupProperties(context: Context) {
        appDbRepository = AppDbRepository(context)
    }

    fun getListGallery() = appDbRepository?.selectAllGallery()

}