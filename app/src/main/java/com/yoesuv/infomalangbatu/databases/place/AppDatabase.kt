package com.yoesuv.infomalangbatu.databases.place

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(PlaceRoom::class)], version =1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDaoAccess() : AppDaoAccess
}