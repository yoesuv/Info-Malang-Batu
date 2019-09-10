package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.gallery.DatabaseListGaleri
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

class FragmentGalleryViewModel: ViewModel() {

    private var listGalleryModel: MutableList<GalleryModel> = mutableListOf()
    var listGalleryResponse: MutableLiveData<MutableList<GalleryModel>> = MutableLiveData()

    private lateinit var appDatabase: AppDatabase

    fun setupProperties(context: Context?){
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, AppConstants.DATABASE_NAME).build()
    }

    fun getListGallery() {
        listGalleryModel.clear()
        val listGallery = DatabaseListGaleri(appDatabase).execute().get()
        for (galleryRoom in listGallery) {
            val galleryModel = GalleryModel(galleryRoom.caption, galleryRoom.thumbnail, galleryRoom.image)
            listGalleryModel.add(galleryModel)
        }
        listGalleryResponse.postValue(listGalleryModel)
    }

}