package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDaoAccess {

    @Insert
    fun insertPlace(placeRoom: PlaceRoom)

    @Query("SELECT * FROM PlaceRoom")
    fun selectAll(): MutableList<PlaceRoom>

    @Delete
    fun deletePlace(placeRoom: PlaceRoom)

}