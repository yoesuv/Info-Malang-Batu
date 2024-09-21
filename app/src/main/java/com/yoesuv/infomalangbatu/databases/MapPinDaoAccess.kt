package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel

@Dao
interface MapPinDaoAccess {

    @Insert
    suspend fun insertDbMapPins(pin: List<PinModel>)

    @Query("SELECT * FROM PinModel")
    suspend fun selectAllDbMapPins(): MutableList<PinModel>

    @Query("DELETE FROM PinModel")
    suspend  fun deleteAllDbMapPins()

}