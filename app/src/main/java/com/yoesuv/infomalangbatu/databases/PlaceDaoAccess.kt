package com.yoesuv.infomalangbatu.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

@Dao
interface PlaceDaoAccess {

    @Insert
    suspend fun insertPlaces(places: List<PlaceModel>)

    @Query("SELECT * FROM PlaceModel")
    fun selectAll(): LiveData<List<PlaceModel>>

    @Query("SELECT * FROM PlaceModel WHERE location= :location")
    fun selectPlaceByLocation(location: String?): LiveData<List<PlaceModel>>

    @Query("DELETE FROM PlaceModel")
    suspend fun deleteAllPlace()

}