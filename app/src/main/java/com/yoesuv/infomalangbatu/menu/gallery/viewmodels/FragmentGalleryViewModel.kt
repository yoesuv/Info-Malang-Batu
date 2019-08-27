package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class FragmentGalleryViewModel: ViewModel() {

    var isLoading: ObservableField<Boolean> = ObservableField()
    var listGalleryResponse: MutableLiveData<MutableList<GalleryModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getGallery(){

    }

    override fun onCleared() {
        super.onCleared()
    }

}