package com.yoesuv.infomalangbatu

import android.app.Application
import android.util.Log
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.utils.PreferencesHelper

class App: Application() {

    companion object {
        var prefHelper: PreferencesHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(AppConstants.TAG_DEBUG,"App # onCreate")
        prefHelper = PreferencesHelper(this)
        if (BuildConfig.BUILD_TYPE.equals("debug", true)){
            Log.d(AppConstants.TAG_DEBUG,"App # build type debug")
        } else {
            Log.d(AppConstants.TAG_DEBUG,"App # build type release")
        }
    }

}