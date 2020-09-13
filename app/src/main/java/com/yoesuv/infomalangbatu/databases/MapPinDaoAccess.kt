package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.map.MapPinsRoom

@Dao
interface MapPinDaoAccess {

    @Insert
    suspend fun insertDbMapPins(mapPinsRoom: MapPinsRoom)

    @Query("SELECT * FROM MapPinsRoom")
    suspend fun selectAllDbMapPins(): MutableList<MapPinsRoom>

    @Query("DELETE FROM MapPinsRoom")
    suspend  fun deleteAllDbMapPins()

}