package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databases.PlaceRoom
import com.yoesuv.infomalangbatu.databases.place.DatabaseDeleteAllPlace
import com.yoesuv.infomalangbatu.databases.place.DatabaseInsertPlace
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.networks.AppRepository

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(viewModelScope)

    var version: ObservableField<String> = ObservableField()

    fun setVersion(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }

    fun initDataBase(context: Context) {
        appRepository.getListPlace({
            DatabaseDeleteAllPlace(context).execute()
            for (placeModel in it!!) {
                val placeRoom = PlaceRoom(placeModel.name, placeModel.location, placeModel.description, placeModel.thumbnail, placeModel.image)
                DatabaseInsertPlace(context, placeRoom).execute()
            }
            openApplication(context)
        },{code, message ->

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