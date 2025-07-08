package com.yoesuv.infomalangbatu.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel

@Database(
    entities = [
        PlaceModel::class,
        GalleryModel::class,
        PinModel::class
    ], version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDaoAccess(): PlaceDaoAccess
    abstract fun galleryDaoAccess(): GalleryDaoAccess
    abstract fun mapPinDaoAccess(): MapPinDaoAccess

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.DATABASE_NAME)
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }
    }


}