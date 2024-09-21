package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

@Dao
interface GalleryDaoAccess {

    @Insert
    suspend fun insertDbGalleries(gallery: List<GalleryModel>)

    @Query("SELECT * FROM GalleryModel")
    suspend fun selectAllDbGallery(): MutableList<GalleryModel>

    @Query("DELETE FROM GalleryModel")
    suspend fun deleteAllDbGallery()

}