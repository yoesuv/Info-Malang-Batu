package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import retrofit2.http.GET
import retrofit2.Response

/**
 *  Updated by yusuf on 8/27/19.
 */
interface RestApi {

    @GET("List_place_malang_batu.json")
    suspend fun getListPlace(): Response<MutableList<PlaceModel>>

    @GET("List_place_kab_malang.json")
    suspend fun getListPlaceKabMalang(): Response<MutableList<PlaceModel>>

    @GET("List_place_kota_batu.json")
    suspend fun getListPlaceKotaBatu(): Response<MutableList<PlaceModel>>

    @GET("List_place_kota_malang.json")
    suspend fun getListPlaceKotaMalang(): Response<MutableList<PlaceModel>>

    @GET("Gallery_Malang_Batu.json")
    suspend fun getGallery(): Response<MutableList<GalleryModel>>

    @GET("Maps_Malang_Batu.json ")
    suspend fun getMapPins(): Response<MutableList<PinModel>>

}