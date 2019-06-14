package com.yoesuv.infomalangbatu

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.utils.PreferencesHelper
import io.fabric.sdk.android.Fabric

class App: MultiDexApplication() {

    companion object {
        var prefHelper: PreferencesHelper? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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