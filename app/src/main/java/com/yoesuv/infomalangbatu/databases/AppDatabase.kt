package com.yoesuv.infomalangbatu.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom
import com.yoesuv.infomalangbatu.databases.map.MapPinsRoom

@Database(entities = [PlaceRoom::class , GaleriRoom::class, MapPinsRoom::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDaoAccess() : AppDaoAccess
}