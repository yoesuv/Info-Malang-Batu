package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.place.PlaceDatabase
import com.yoesuv.infomalangbatu.databases.place.PlaceRoom
import com.yoesuv.infomalangbatu.databases.place.DatabaseDeleteAllPlace
import com.yoesuv.infomalangbatu.databases.place.DatabaseInsertPlace
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.networks.AppRepository

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(viewModelScope)
    private lateinit var placeDatabase: PlaceDatabase

    var version: ObservableField<String> = ObservableField()

    fun setupProperties(activity: Activity) {
        version.set(activity.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
        placeDatabase = Room.databaseBuilder(activity, PlaceDatabase::class.java, AppConstants.DATABASE_NAME).build()
    }

    fun initDataBase(activity: Activity) {
        appRepository.getListPlace({
            DatabaseDeleteAllPlace(placeDatabase).execute()
            for (placeModel in it!!) {
                val placeRoom = PlaceRoom(
                    placeModel.name,
                    placeModel.location,
                    placeModel.description,
                    placeModel.thumbnail,
                    placeModel.image
                )
                DatabaseInsertPlace(placeDatabase, placeRoom).execute()
            }
            initDataGallery(activity)
        },{ code, message ->

        },{

        })
    }

    private fun initDataGallery(activity: Activity) {
        appRepository.getListGallery({
            initDataMapPins(activity)
        },{ code, message ->

        },{

        })
    }

    private fun initDataMapPins(activity: Activity) {
        appRepository.getListMapPins({
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