package com.yoesuv.infomalangbatu.main.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivitySplashBinding
import com.yoesuv.infomalangbatu.main.viewmodels.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        binding.splash = viewModel

        viewModel.setupProperties(this)
        viewModel.initDataBase(this)
    }

}