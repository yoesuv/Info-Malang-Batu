package com.yoesuv.infomalangbatu

import android.app.Application
import android.util.Log
import com.yoesuv.infomalangbatu.data.AppConstants

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(AppConstants.TAG_DEBUG,"App # onCreate")
    }

}