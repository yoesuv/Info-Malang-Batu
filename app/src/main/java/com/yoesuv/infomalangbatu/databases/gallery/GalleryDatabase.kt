package com.yoesuv.infomalangbatu.databases.gallery

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GalleryRoom::class], version = 1)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun galleryDaoAccess(): GalleryDaoAccess
}