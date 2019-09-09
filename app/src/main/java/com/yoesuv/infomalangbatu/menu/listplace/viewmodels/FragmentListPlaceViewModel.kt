package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.room.Room
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.place.DatabaseListPlace
import com.yoesuv.infomalangbatu.databases.place.AppDatabase
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class FragmentListPlaceViewModel: ViewModel() {

    var isLoading: ObservableField<Boolean> = ObservableField()
    var listPlaceModel: MutableList<PlaceModel> = mutableListOf()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    private lateinit var appDatabase: AppDatabase

    fun setupProperties(context: Context?) {
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, AppConstants.DATABASE_NAME).build()
    }

    fun getListPlace(){
        listPlaceModel.clear()
        val listPlace = DatabaseListPlace(appDatabase).execute().get()
        for (placeRoom in listPlace) {
            val placeModel = PlaceModel(placeRoom.name, placeRoom.location, "", placeRoom.description, placeRoom.thumbnail, placeRoom.image)
            listPlaceModel.add(placeModel)
        }
        listPlaceResponse.postValue(listPlaceModel)
    }

    fun getListPlaceKabMalang(){

    }

    fun getListPlaceKotaBatu(){

    }

    fun getListPlaceKotaMalang(){

    }

}