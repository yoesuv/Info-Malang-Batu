package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom

@Dao
interface AppDaoAccess {

    @Insert
    fun insertPlace(placeRoom: PlaceRoom)

    @Query("SELECT * FROM PlaceRoom")
    fun selectAll(): MutableList<PlaceRoom>

    @Query("DELETE FROM PlaceRoom")
    fun deleteAllPlace()

    @Insert
    fun insertGallery(galeriRoom: GaleriRoom)

    @Query("SELECT * FROM GaleriRoom")
    fun selectAllGallery(): MutableList<GaleriRoom>

    @Query("DELETE FROM GaleriRoom")
    fun deleteAllGallery()

}