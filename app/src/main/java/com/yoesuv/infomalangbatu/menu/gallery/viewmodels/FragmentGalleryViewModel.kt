package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import kotlinx.coroutines.launch

class FragmentGalleryViewModel: ViewModel() {

    private var listGalleryModel: MutableList<GalleryModel> = mutableListOf()
    var listGalleryResponse: MutableLiveData<MutableList<GalleryModel>> = MutableLiveData()

    private var appDatabase: AppDatabase? = null

    fun setupProperties(context: Context?){
        appDatabase = AppDatabase.getInstance(context!!)
    }

    fun getListGallery() {
        viewModelScope.launch {
            listGalleryModel.clear()
            appDatabase?.galleryDaoAccess()?.selectAllDbGallery()?.forEach {
                val galleryModel = GalleryModel(it.caption, it.thumbnail, it.image)
                listGalleryModel.add(galleryModel)
            }
            listGalleryResponse.postValue(listGalleryModel)
        }
    }

}