package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

@Dao
interface PlaceDaoAccess {

    @Insert
    suspend fun insertPlace(place: PlaceModel)

    @Query("SELECT * FROM PlaceModel")
    suspend fun selectAll(): MutableList<PlaceModel>

    @Query("SELECT * FROM PlaceModel WHERE location= :location")
    suspend fun selectPlaceByLocation(location: String?): MutableList<PlaceModel>

    @Query("DELETE FROM PlaceModel")
    suspend fun deleteAllPlace()

}