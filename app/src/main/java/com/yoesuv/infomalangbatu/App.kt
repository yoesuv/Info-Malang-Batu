package com.yoesuv.infomalangbatu

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.utils.PreferencesHelper
import io.fabric.sdk.android.Fabric

class App: Application() {

    companion object {
        var prefHelper: PreferencesHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(AppConstants.TAG_DEBUG,"App # onCreate")
        prefHelper = PreferencesHelper(this)
        if (BuildConfig.FLAVOR.equals("production", true)) {
            Log.d(AppConstants.TAG_DEBUG,"App # product flavor PRODUCTION")
            Fabric.with(this, Crashlytics())
        } else {
            Log.d(AppConstants.TAG_DEBUG,"App # product flavor DEVELOPMENT")
        }
    }

}