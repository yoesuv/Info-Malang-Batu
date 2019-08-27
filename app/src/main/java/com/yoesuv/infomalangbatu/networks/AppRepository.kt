package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppRepository(private val coroutineScope: CoroutineScope) {

    private val restApi = ServiceFactory.create()

    fun getListPlace(onSuccess:(MutableList<PlaceModel>?) -> Unit, onUnSuccess:(Int, String) -> Unit, onError:(Throwable) -> Unit) {
        coroutineScope.launch {
            val result = restApi.getListPlace()
            try {
                if (result.isSuccessful) {
                    onSuccess(result.body())
                } else {
                    onUnSuccess(result.code(), result.message())
                }
            } catch (error: Throwable) {
                onError(error)
            }
        }
    }

    fun getListGallery(onSuccess:(MutableList<GalleryModel>?) -> Unit, onUnSuccess:(Int, String) -> Unit, onError:(Throwable) -> Unit) {
        coroutineScope.launch {
            try {
                val result = restApi.getGallery()
                if (result.isSuccessful) {
                    onSuccess(result.body())
                } else {
                    onUnSuccess(result.code(), result.message())
                }
            } catch (error: Throwable) {
                onError(error)
            }
        }
    }

    fun getListMapPins(onSuccess:(MutableList<PinModel>?) -> Unit, onUnSuccess:(Int, String) -> Unit, onError:(Throwable) -> Unit) {
        coroutineScope.launch {
            try {
                val result = restApi.getMapPins()
                if (result.isSuccessful) {
                    onSuccess(result.body())
                } else {
                    onUnSuccess(result.code(), result.message())
                }
            } catch (error: Throwable) {
                onError(error)
            }
        }
    }

}