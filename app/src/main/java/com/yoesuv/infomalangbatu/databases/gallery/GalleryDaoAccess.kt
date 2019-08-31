package com.yoesuv.infomalangbatu.databases.gallery

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GalleryDaoAccess {

    @Insert
    fun insertGallery(galleryRoom: GalleryRoom)

    @Query("SELECT * FROM GalleryRoom")
    fun selectAll(): MutableList<GalleryRoom>

    @Query("DELETE FROM GalleryRoom")
    fun deleteAllGallery()
}