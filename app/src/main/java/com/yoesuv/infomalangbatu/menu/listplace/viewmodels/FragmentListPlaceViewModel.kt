package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDbRepository

class FragmentListPlaceViewModel(private val appDbRepository: AppDbRepository) : ViewModel() {

    fun getListPlaceAll() = appDbRepository.selectAllPlace()

    fun getListPlace(placeLocation: PlaceLocation) = appDbRepository.selectPlaceByLocation(placeLocation.toString())
}