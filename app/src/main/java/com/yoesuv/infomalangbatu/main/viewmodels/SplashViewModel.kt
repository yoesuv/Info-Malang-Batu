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
import com.yoesuv.infomalangbatu.databases.gallery.GaleriRoom
import com.yoesuv.infomalangbatu.databases.map.MapPinsRoom
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.networks.AppRepository
import com.yoesuv.infomalangbatu.utils.AppHelper
import kotlinx.coroutines.launch

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
        appRepository.getAppData({ places, galleries, pins ->
            viewModelScope.launch {
                setupPlaces(places)
                setupGalleries(galleries)
                setupMapPins(pins)
                openApplication(activity)
            }
        }, {
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_place))
            activity.finish()
        })
    }

    private suspend fun setupPlaces(places: MutableList<PlaceModel>?) {
        appDatabase.placeDaoAccess().deleteAllPlace()
        places?.forEach { placeModel ->
            val placeRoom = PlaceRoom(
                placeModel.name,
                placeModel.location,
                placeModel.description,
                placeModel.thumbnail,
                placeModel.image
            )
            appDatabase.placeDaoAccess().insertPlace(placeRoom)
        }
    }

    private suspend fun setupGalleries(galleries: MutableList<GalleryModel>?) {
        appDatabase.galleryDaoAccess().deleteAllDbGallery()
        galleries?.forEach {galleryModel ->
            val galeriRoom = GaleriRoom(
                galleryModel.caption,
                galleryModel.thumbnail,
                galleryModel.image
            )
            appDatabase.galleryDaoAccess().insertDbGallery(galeriRoom)
        }
    }

    private suspend fun setupMapPins(pins: MutableList<PinModel>?) {
        appDatabase.mapPinDaoAccess().deleteAllDbMapPins()
        pins?.forEach { pinModel ->
            val mapPinRoom = MapPinsRoom(
                pinModel.name,
                pinModel.location,
                pinModel.latitude,
                pinModel.longitude
            )
            appDatabase.mapPinDaoAccess().insertDbMapPins(mapPinRoom)
        }
    }

    private fun openApplication(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
        activity.finish()
    }
}