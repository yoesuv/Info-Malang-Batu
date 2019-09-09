package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom

@Dao
interface AppDaoAccess {

    @Insert
    fun insertPlace(placeRoom: PlaceRoom)

    @Query("SELECT * FROM PlaceRoom")
    fun selectAll(): MutableList<PlaceRoom>

    @Query("DELETE FROM PlaceRoom")
    fun deleteAllPlace()

    @Query("DELETE FROM GaleriRoom")
    fun deleteAllGallery()

}