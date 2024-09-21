package com.yoesuv.infomalangbatu.databases

import android.content.Context
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel

class AppDbRepository(context: Context) {
    private val appDatabase = AppDatabase.getInstance(context)

    suspend fun insertPlaces(places: MutableList<PlaceModel>) {
        appDatabase?.placeDaoAccess()?.insertPlaces(places)
    }

    suspend fun deleteAllPlace() {
        appDatabase?.placeDaoAccess()?.deleteAllPlace()
    }

    suspend fun deleteAllGallery() {
        appDatabase?.galleryDaoAccess()?.deleteAllDbGallery()
    }

    suspend fun insertGalleries(galleries: MutableList<GalleryModel>) {
        appDatabase?.galleryDaoAccess()?.insertDbGalleries(galleries)
    }

    suspend fun insertMapPins(pins: MutableList<PinModel>) {
        appDatabase?.mapPinDaoAccess()?.insertDbMapPins(pins)
    }

    suspend fun deleteAllMapPins() {
        appDatabase?.mapPinDaoAccess()?.deleteAllDbMapPins()
    }
}