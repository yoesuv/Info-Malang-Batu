package com.yoesuv.infomalangbatu.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel

@Dao
interface MapPinDaoAccess {

    @Insert
    suspend fun insertDbMapPins(pin: List<PinModel>)

    @Query("SELECT * FROM PinModel")
    fun selectAllDbMapPins(): LiveData<List<PinModel>>

    @Query("DELETE FROM PinModel")
    suspend  fun deleteAllDbMapPins()

}