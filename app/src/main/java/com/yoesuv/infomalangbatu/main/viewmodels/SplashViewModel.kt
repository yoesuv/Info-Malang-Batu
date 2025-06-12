package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.networks.AppRepository
import kotlinx.coroutines.launch

class SplashViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {

    var appDbRepository = AppDbRepository(application.applicationContext)

    var version: ObservableField<String> = ObservableField()
    private val _isDataLoadingComplete = MutableLiveData<Boolean>()
    val isDataLoadingComplete: LiveData<Boolean> = _isDataLoadingComplete

    fun setupProperties(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }

    fun initDataBase(onError: () -> Unit) {
        _isDataLoadingComplete.value = false
        viewModelScope.launch {
            appRepository.getAppData({ places, galleries, pins ->
                viewModelScope.launch {
                    setupPlaces(places)
                    setupGalleries(galleries)
                    setupMapPins(pins)
                    _isDataLoadingComplete.value = true
                }
            }, {
                onError()
            })
        }
    }

    private suspend fun setupPlaces(places: MutableList<PlaceModel>?) {
        appDbRepository.deleteAllPlace()
        if (places != null) {
            appDbRepository.insertPlaces(places)
        }
    }

    private suspend fun setupGalleries(galleries: MutableList<GalleryModel>?) {
        appDbRepository.deleteAllGallery()
        if (galleries != null) {
            appDbRepository.insertGalleries(galleries)
        }
    }

    private suspend fun setupMapPins(pins: MutableList<PinModel>?) {
        appDbRepository.deleteAllMapPins()
        if (pins != null) {
            appDbRepository.insertMapPins(pins)
        }
    }
}