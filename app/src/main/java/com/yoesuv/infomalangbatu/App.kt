package com.yoesuv.infomalangbatu

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yoesuv.infomalangbatu.utils.PreferencesHelper

class App: Application() {

    companion object {
        var prefHelper: PreferencesHelper? = null
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