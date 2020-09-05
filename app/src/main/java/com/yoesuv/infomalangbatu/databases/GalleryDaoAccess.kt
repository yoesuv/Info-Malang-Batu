package com.yoesuv.infomalangbatu.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom

@Dao
interface GalleryDaoAccess {

    @Insert
    suspend fun insertDbGallery(galeriRoom: GaleriRoom)

    @Query("SELECT * FROM GaleriRoom")
    suspend fun selectAllDbGallery(): MutableList<GaleriRoom>

    @Query("DELETE FROM GaleriRoom")
    suspend fun deleteAllDbGallery()

}