package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yoesuv.infomalangbatu.utils.PreferencesHelper

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
        prefHelper = PreferencesHelper(this)
        setupFirebaseCrashlytics()
    }

    private fun setupFirebaseCrashlytics() {
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }

}