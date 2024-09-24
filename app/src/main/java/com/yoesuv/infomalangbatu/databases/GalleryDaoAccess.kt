package com.yoesuv.infomalangbatu.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel

@Dao
interface GalleryDaoAccess {

    @Insert
    suspend fun insertDbGalleries(gallery: List<GalleryModel>)

    @Query("SELECT * FROM GalleryModel")
    fun selectAllDbGallery(): LiveData<List<GalleryModel>>

    @Query("DELETE FROM GalleryModel")
    suspend fun deleteAllDbGallery()

}