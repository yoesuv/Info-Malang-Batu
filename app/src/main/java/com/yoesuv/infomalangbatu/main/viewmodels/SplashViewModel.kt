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
        viewModelScope.launch {
            appDatabase.placeDaoAccess().deleteAllPlace()
        }
        appRepository.getListPlace({
            for (placeModel in it!!) {
                val placeRoom = PlaceRoom(
                    placeModel.name,
                    placeModel.location,
                    placeModel.description,
                    placeModel.thumbnail,
                    placeModel.image
                )
                viewModelScope.launch {
                    appDatabase.placeDaoAccess().insertPlace(placeRoom)
                }
            }
            initDataGallery(activity)
        },{ code, message ->
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_place))
            activity.finish()
        },{
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_place))
            activity.finish()
        })
    }

    private fun initDataGallery(activity: Activity) {
        viewModelScope.launch {
            appDatabase.galleryDaoAccess().deleteAllDbGallery()
        }
        appRepository.getListGallery({
            for (galleryModel in it!!) {
                val galeriRoom = GaleriRoom(
                    galleryModel.caption,
                    galleryModel.thumbnail,
                    galleryModel.image
                )
                viewModelScope.launch {
                    appDatabase.galleryDaoAccess().insertDbGallery(galeriRoom)
                }
            }
            initDataMapPins(activity)
        },{ code, message ->
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_gallery))
            activity.finish()
        },{
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_gallery))
            activity.finish()
        })
    }

    private fun initDataMapPins(activity: Activity) {
        viewModelScope.launch {
            appDatabase.mapPinDaoAccess().deleteAllDbMapPins()
        }
        appRepository.getListMapPins({
            for (pinModel in it!!) {
                val mapPinRoom = MapPinsRoom(
                    pinModel.name,
                    pinModel.location,
                    pinModel.latitude,
                    pinModel.longitude
                )
                viewModelScope.launch {
                    appDatabase.mapPinDaoAccess().insertDbMapPins(mapPinRoom)
                }
            }
            openApplication(activity)
        },{ code, message ->
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_map_pins))
            activity.finish()
        },{
            AppHelper.displayToastError(activity,activity.getString(R.string.toast_error_get_list_map_pins))
            activity.finish()
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