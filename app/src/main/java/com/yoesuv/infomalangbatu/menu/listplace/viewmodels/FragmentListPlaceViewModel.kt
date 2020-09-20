package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import kotlinx.coroutines.launch

class FragmentListPlaceViewModel: ViewModel() {

    private var listPlaceModel: MutableList<PlaceModel> = mutableListOf()
    private var listPlaceRoom: MutableList<PlaceRoom> = mutableListOf()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()

    private lateinit var appDatabase: AppDatabase

    fun setupProperties(context: Context?) {
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, AppConstants.DATABASE_NAME).build()
    }

    fun getListPlace(placeLocation: PlaceLocation){
        viewModelScope.launch {
            listPlaceModel.clear()
            listPlaceRoom.clear()
            if (placeLocation == PlaceLocation.KOTA_MALANG) {
                listPlaceRoom = appDatabase.placeDaoAccess().selectPlaceByLocation(placeLocation.toString())
            } else if (placeLocation == PlaceLocation.KAB_MALANG) {
                listPlaceRoom = appDatabase.placeDaoAccess().selectPlaceByLocation(placeLocation.toString())
            } else if (placeLocation == PlaceLocation.KOTA_BATU) {
                listPlaceRoom = appDatabase.placeDaoAccess().selectPlaceByLocation(placeLocation.toString())
            } else {
                listPlaceRoom = appDatabase.placeDaoAccess().selectAll()
            }
            for (placeRoom in listPlaceRoom) {
                val placeModel = PlaceModel(placeRoom.name, placeRoom.location, "", placeRoom.description, placeRoom.thumbnail, placeRoom.image)
                listPlaceModel.add(placeModel)
            }
            listPlaceResponse.postValue(listPlaceModel)

        }
    }

}