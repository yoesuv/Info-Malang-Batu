package com.yoesuv.infomalangbatu.main.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivitySplashBinding
import com.yoesuv.infomalangbatu.main.viewmodels.SplashViewModel
import com.yoesuv.infomalangbatu.networks.AppRepositoryImpl
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.SplashViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppHelper.isVanillaIceCreamAndUp()) {
            enableEdgeToEdge()
        }

        val appRepository = AppRepositoryImpl()
        val viewModelFactory = SplashViewModelFactory(application, appRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.splash = viewModel

        viewModel.setupProperties(this)
        viewModel.initDataBase(this)

        AppHelper.insetsPadding(binding.rlSplash, bottom = true)
    }

}