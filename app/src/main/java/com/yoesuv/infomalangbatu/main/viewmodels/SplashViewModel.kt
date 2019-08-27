package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Application
import android.content.Context

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.networks.ServiceFactory

import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val restApi = ServiceFactory.create()

    var version: ObservableField<String> = ObservableField()

    fun setVersion(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }

    fun initDataBase(context: Context) {
        viewModelScope.launch {
            try {
                val result = restApi.getListPlace()
                if (result.isSuccessful) {

                } else {

                }
            } catch (error: Exception) {

            } finally {

            }
        }
    }
}