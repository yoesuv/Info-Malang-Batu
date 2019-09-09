package com.yoesuv.infomalangbatu.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom

@Database(entities = [PlaceRoom::class , GaleriRoom::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDaoAccess() : AppDaoAccess
}