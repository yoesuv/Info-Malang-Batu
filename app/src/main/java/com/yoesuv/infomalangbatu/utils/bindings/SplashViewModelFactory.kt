package com.yoesuv.infomalangbatu.utils.bindings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.main.viewmodels.SplashViewModel
import com.yoesuv.infomalangbatu.networks.AppRepository

class SplashViewModelFactory(
    private val application: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(application, appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}