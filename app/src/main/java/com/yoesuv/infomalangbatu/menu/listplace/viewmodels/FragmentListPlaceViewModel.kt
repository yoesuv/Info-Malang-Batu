package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import kotlinx.coroutines.launch

class FragmentListPlaceViewModel: ViewModel() {

    private var listPlaceModel: MutableList<PlaceModel> = mutableListOf()
    private var listPlaceRoom: MutableList<PlaceModel> = mutableListOf()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()

    private var appDatabase: AppDatabase? = null

    fun setupProperties(context: Context?) {
        appDatabase = AppDatabase.getInstance(context!!)
    }

    fun getListPlace(placeLocation: PlaceLocation){
        viewModelScope.launch {
            listPlaceModel.clear()
            listPlaceRoom.clear()
            if (placeLocation == PlaceLocation.KOTA_MALANG) {
                appDatabase?.placeDaoAccess()?.selectPlaceByLocation(placeLocation.toString())?.let {
                    listPlaceRoom = it
                }
            } else if (placeLocation == PlaceLocation.KAB_MALANG) {
                appDatabase?.placeDaoAccess()?.selectPlaceByLocation(placeLocation.toString())?.let {
                    listPlaceRoom = it
                }
            } else if (placeLocation == PlaceLocation.KOTA_BATU) {
                appDatabase?.placeDaoAccess()?.selectPlaceByLocation(placeLocation.toString())?.let {
                    listPlaceRoom = it
                }
            } else {
                appDatabase?.placeDaoAccess()?.selectAll()?.let {
                    listPlaceRoom = it
                }
            }
            for (place in listPlaceRoom) {
                listPlaceModel.add(place)
            }
            listPlaceResponse.postValue(listPlaceModel)
        }
    }

}