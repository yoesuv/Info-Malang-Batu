package com.yoesuv.infomalangbatu.main.viewmodels

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    var version: ObservableField<String> = ObservableField()

    fun setVersion(context: Context) {
        version.set(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME))
    }
}