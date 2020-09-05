package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.map.MapPinsRoom
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom

@Dao
interface AppDaoAccess {

    /* list place */
    @Insert
    fun insertPlace(placeRoom: PlaceRoom)

    @Query("SELECT * FROM PlaceRoom")
    fun selectAll(): MutableList<PlaceRoom>

    @Query("SELECT * FROM PlaceRoom WHERE location= :location")
    fun selectPlaceByLocation(location: String?): MutableList<PlaceRoom>

    @Query("DELETE FROM PlaceRoom")
    fun deleteAllPlace()

    /* map pins */
    @Insert
    fun insertMapPins(mapPinsRoom: MapPinsRoom)

    @Query("SELECT * FROM MapPinsRoom")
    fun selectAllMapPins(): MutableList<MapPinsRoom>

    @Query("DELETE FROM MapPinsRoom")
    fun deleteAllMapPins()

}