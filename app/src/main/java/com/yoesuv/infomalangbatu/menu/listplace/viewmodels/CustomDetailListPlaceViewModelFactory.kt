package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class CustomDetailListPlaceViewModelFactory(private val placeModel: PlaceModel?, private val application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailListPlaceViewModel(placeModel, application) as T
    }

}