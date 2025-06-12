package com.yoesuv.infomalangbatu.mock

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.networks.AppRepository
import com.yoesuv.infomalangbatu.utils.JsonParser

class AppRepositoryMock : AppRepository {
    override suspend fun getAppData(
        onSuccess: (MutableList<PlaceModel>?, MutableList<GalleryModel>?, MutableList<PinModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val places = JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java).toMutableList()
            val galleries = JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java).toMutableList()
            val pins = JsonParser.stringToObject("maps_pin.json", Array<PinModel>::class.java).toMutableList()
            onSuccess(places, galleries, pins)
        } catch (e: Exception) {
            onError(e)
        }
    }
}