package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.place.DatabaseListPlace
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.place.DatabaseListPlaceByLocation
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class FragmentListPlaceViewModel: ViewModel() {

    private var listPlaceModel: MutableList<PlaceModel> = mutableListOf()
    private var listPlaceRoom: MutableList<PlaceRoom> = mutableListOf()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    private lateinit var appDatabase: AppDatabase

    fun setupProperties(context: Context?) {
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, AppConstants.DATABASE_NAME).build()
    }

    fun getListPlace(placeLocation: PlaceLocation){

        listPlaceModel.clear()
        listPlaceRoom.clear()

        if (placeLocation == PlaceLocation.KOTA_MALANG) {
            listPlaceRoom = DatabaseListPlaceByLocation(appDatabase, placeLocation.toString()).execute().get()
        } else if (placeLocation == PlaceLocation.KAB_MALANG) {
            listPlaceRoom = DatabaseListPlaceByLocation(appDatabase, placeLocation.toString()).execute().get()
        } else if (placeLocation == PlaceLocation.KOTA_BATU) {
            listPlaceRoom = DatabaseListPlaceByLocation(appDatabase, placeLocation.toString()).execute().get()
        } else {
            listPlaceRoom = DatabaseListPlace(appDatabase).execute().get()
        }

        for (placeRoom in listPlaceRoom) {
            val placeModel = PlaceModel(placeRoom.name, placeRoom.location, "", placeRoom.description, placeRoom.thumbnail, placeRoom.image)
            listPlaceModel.add(placeModel)
        }
        listPlaceResponse.postValue(listPlaceModel)
    }

}