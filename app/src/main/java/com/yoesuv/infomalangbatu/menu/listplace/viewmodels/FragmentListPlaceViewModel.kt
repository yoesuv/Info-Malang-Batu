package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import kotlinx.coroutines.launch

class FragmentListPlaceViewModel : ViewModel() {

    private var listPlaceModel: MutableList<PlaceModel> = mutableListOf()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()

    private var appDbRepository: AppDbRepository? = null

    fun setupProperties(context: Context) {
        appDbRepository = AppDbRepository(context)
    }

    fun getListPlace(placeLocation: PlaceLocation) {
        viewModelScope.launch {
            listPlaceModel.clear()
            if (placeLocation == PlaceLocation.ALL) {
                appDbRepository?.selectAllPlace()?.let {
                    listPlaceModel.addAll(it)
                }
            } else {
                appDbRepository?.selectPlaceByLocation(placeLocation.toString())?.let {
                    listPlaceModel.addAll(it)
                }
            }
            listPlaceResponse.postValue(listPlaceModel)
        }
    }

}