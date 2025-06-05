package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.databases.AppDbRepository

class FragmentGalleryViewModel(private val appDbRepository: AppDbRepository) : ViewModel() {

    fun getListGallery() = appDbRepository.selectAllGallery()
}