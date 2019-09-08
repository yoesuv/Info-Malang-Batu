package com.yoesuv.infomalangbatu.databases.place

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(PlaceRoom::class)], version =1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun appDaoAccess() : PlaceDaoAccess
}