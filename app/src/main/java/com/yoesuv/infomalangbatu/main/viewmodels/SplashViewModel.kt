package com.yoesuv.infomalangbatu.main.viewmodels

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

    fun setVersion(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }

    fun initDataBase(context: Context) {
        placeDatabase = Room.databaseBuilder(context, PlaceDatabase::class.java, AppConstants.DATABASE_NAME).build()
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
            initDataGallery(context)
        },{ code, message ->

        },{

        })
    }

    private fun initDataGallery(context: Context) {
        appRepository.getListGallery({
            initDataMapPins(context)
        },{ code, message ->

        },{

        })
    }

    private fun initDataMapPins(context: Context) {
        appRepository.getListMapPins({
            openApplication(context)
        },{ code, message ->

        },{

        })
    }

    private fun openApplication(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
}