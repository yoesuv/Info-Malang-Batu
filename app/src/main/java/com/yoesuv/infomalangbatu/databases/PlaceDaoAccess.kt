package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom

@Dao
interface PlaceDaoAccess {

    @Insert
    suspend fun insertPlace(placeRoom: PlaceRoom)

    @Query("SELECT * FROM PlaceRoom")
    suspend fun selectAll(): MutableList<PlaceRoom>

    @Query("SELECT * FROM PlaceRoom WHERE location= :location")
    suspend fun selectPlaceByLocation(location: String?): MutableList<PlaceRoom>

    @Query("DELETE FROM PlaceRoom")
    suspend fun deleteAllPlace()

}