package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import retrofit2.http.GET
import retrofit2.Response

/**
 *  Updated by yusuf on 13/ Sep /20.
 */
interface RestApi {

    @GET("List_place_malang_batu.json")
    suspend fun getListPlace(): Response<MutableList<PlaceModel>>

    @GET("Gallery_Malang_Batu.json")
    suspend fun getGallery(): Response<MutableList<GalleryModel>>

    @GET("Maps_Malang_Batu.json ")
    suspend fun getMapPins(): Response<MutableList<PinModel>>

}