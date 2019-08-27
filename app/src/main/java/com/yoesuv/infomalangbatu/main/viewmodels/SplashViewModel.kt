package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Application
import android.content.Context

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.networks.AppRepository

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(viewModelScope)

    var version: ObservableField<String> = ObservableField()

    fun setVersion(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }

    fun initDataBase(context: Context) {
        appRepository.getListPlace(context, {

        },{code, message ->

        },{

        })
    }
}