package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.gallery.DatabaseDeleteAllGaleri
import com.yoesuv.infomalangbatu.databases.gallery.DatabaseInsertGaleri
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom
import com.yoesuv.infomalangbatu.databases.map.DatabaseDeleteAllMapPins
import com.yoesuv.infomalangbatu.databases.map.DatabaseInsertMapPins
import com.yoesuv.infomalangbatu.databases.map.MapPinsRoom
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.databases.place.DatabaseDeleteAllPlace
import com.yoesuv.infomalangbatu.databases.place.DatabaseInsertPlace
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.networks.AppRepository

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(viewModelScope)
    private lateinit var appDatabase: AppDatabase

    var version: ObservableField<String> = ObservableField()

    fun setupProperties(activity: Activity) {
        version.set(activity.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
        appDatabase = Room.databaseBuilder(activity, AppDatabase::class.java, AppConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun initDataBase(activity: Activity) {
        appRepository.getListPlace({
            DatabaseDeleteAllPlace(appDatabase).execute()
            for (placeModel in it!!) {
                val placeRoom = PlaceRoom(
                    placeModel.name,
                    placeModel.location,
                    placeModel.description,
                    placeModel.thumbnail,
                    placeModel.image
                )
                DatabaseInsertPlace(appDatabase, placeRoom).execute()
            }
            initDataGallery(activity)
        },{ code, message ->

        },{

        })
    }

    private fun initDataGallery(activity: Activity) {
        appRepository.getListGallery({
            DatabaseDeleteAllGaleri(appDatabase).execute()
            for (galleryModel in it!!) {
                val galeriRoom = GaleriRoom(
                    galleryModel.caption,
                    galleryModel.thumbnail,
                    galleryModel.image
                )
                DatabaseInsertGaleri(appDatabase, galeriRoom).execute()
            }
            initDataMapPins(activity)
        },{ code, message ->

        },{

        })
    }

    private fun initDataMapPins(activity: Activity) {
        appRepository.getListMapPins({
            DatabaseDeleteAllMapPins(appDatabase).execute()
            for (pinModel in it!!) {
                val mapPinRoom = MapPinsRoom(
                    pinModel.name,
                    pinModel.location,
                    pinModel.latitude,
                    pinModel.longitude
                )
                DatabaseInsertMapPins(appDatabase, mapPinRoom).execute()
            }
            openApplication(activity)
        },{ code, message ->

        },{

        })
    }

    private fun openApplication(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
        activity.finish()
    }
}