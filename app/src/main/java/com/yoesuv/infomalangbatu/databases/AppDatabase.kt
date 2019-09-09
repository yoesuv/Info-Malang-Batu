package com.yoesuv.infomalangbatu.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom

@Database(entities = [(PlaceRoom::class)], version =1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDaoAccess() : AppDaoAccess
}