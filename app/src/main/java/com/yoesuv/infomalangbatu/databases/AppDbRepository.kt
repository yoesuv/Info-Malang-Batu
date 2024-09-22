package com.yoesuv.infomalangbatu.databases

import android.content.Context
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel

class AppDbRepository(context: Context) {
    private val appDatabase = AppDatabase.getInstance(context)

    // place
    suspend fun insertPlaces(places: MutableList<PlaceModel>) {
        appDatabase?.placeDaoAccess()?.insertPlaces(places)
    }

    fun selectAllPlace() = appDatabase?.placeDaoAccess()?.selectAll()

    fun selectPlaceByLocation(location: String) = appDatabase?.placeDaoAccess()?.selectPlaceByLocation(location)

    suspend fun deleteAllPlace() {
        appDatabase?.placeDaoAccess()?.deleteAllPlace()
    }

    // gallery
    fun selectAllGallery() = appDatabase?.galleryDaoAccess()?.selectAllDbGallery()

    suspend fun insertGalleries(galleries: MutableList<GalleryModel>) {
        appDatabase?.galleryDaoAccess()?.insertDbGalleries(galleries)
    }

    suspend fun deleteAllGallery() {
        appDatabase?.galleryDaoAccess()?.deleteAllDbGallery()
    }

    // map pins
    suspend fun insertMapPins(pins: MutableList<PinModel>) {
        appDatabase?.mapPinDaoAccess()?.insertDbMapPins(pins)
    }

    fun selectAllMapPins() = appDatabase?.mapPinDaoAccess()?.selectAllDbMapPins()

    suspend fun deleteAllMapPins() {
        appDatabase?.mapPinDaoAccess()?.deleteAllDbMapPins()
    }
}