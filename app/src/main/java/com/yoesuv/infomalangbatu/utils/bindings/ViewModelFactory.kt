package com.yoesuv.infomalangbatu.utils.bindings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel

class ViewModelFactory(private val repository: AppDbRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FragmentListPlaceViewModel::class.java) -> {
                FragmentListPlaceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FragmentGalleryViewModel::class.java) -> {
                FragmentGalleryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
