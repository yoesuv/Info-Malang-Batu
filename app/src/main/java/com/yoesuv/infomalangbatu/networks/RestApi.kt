package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

/**
 *  Created by yusuf on 4/30/18.
 */
interface RestApi {

    @GET("List_place_malang_batu.json")
    fun getListPlace(): Observable<MutableList<PlaceModel>>

    @GET("Gallery_Malang_Batu.json")
    fun getGallery(): Observable<MutableList<GalleryModel>>

}