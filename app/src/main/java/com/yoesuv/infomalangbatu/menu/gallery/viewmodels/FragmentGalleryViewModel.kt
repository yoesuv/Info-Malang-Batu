package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import kotlinx.coroutines.launch

class FragmentGalleryViewModel : ViewModel() {

    private var listGalleryModel: MutableList<GalleryModel> = mutableListOf()
    var listGalleryResponse: MutableLiveData<MutableList<GalleryModel>> = MutableLiveData()

    private var appDbRepository: AppDbRepository? = null

    fun setupProperties(context: Context) {
        appDbRepository = AppDbRepository(context)
    }

    fun getListGallery() {
        viewModelScope.launch {
            listGalleryModel.clear()
            appDbRepository?.selectAllGallery()?.let {
                listGalleryModel.addAll(it)
            }
            listGalleryResponse.postValue(listGalleryModel)
        }
    }

}