package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppRepository(private val coroutineScope: CoroutineScope) {

    private val restApi = ServiceFactory.create()

    fun getAppData(onSuccess:(MutableList<PlaceModel>?, MutableList<GalleryModel>?, MutableList<PinModel>?) -> Unit, onError:(Throwable) -> Unit) {
        coroutineScope.launch {
            try {
                val resultListPlace = restApi.getListPlace()
                val resultGallery = restApi.getGallery()
                val resultMapPins = restApi.getMapPins()

                onSuccess(resultListPlace.body(), resultGallery.body(), resultMapPins.body())
            } catch (throwable: Throwable) {
                onError(throwable)
            }
        }
    }

}