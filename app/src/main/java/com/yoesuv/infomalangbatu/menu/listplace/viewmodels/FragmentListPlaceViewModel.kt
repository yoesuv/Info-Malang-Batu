package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDbRepository

class FragmentListPlaceViewModel : ViewModel() {

    private var appDbRepository: AppDbRepository? = null

    fun setupProperties(context: Context) {
        appDbRepository = AppDbRepository(context)
    }

    fun getListPlaceAll() = appDbRepository?.selectAllPlace()

    fun getListPlace(placeLocation: PlaceLocation) = appDbRepository?.selectPlaceByLocation(placeLocation.toString())

}