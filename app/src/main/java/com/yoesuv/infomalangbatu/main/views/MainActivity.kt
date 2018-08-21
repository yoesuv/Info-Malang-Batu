package com.yoesuv.infomalangbatu.main.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivityMainBinding
import com.yoesuv.infomalangbatu.main.viewmodels.MainViewModel
import com.yoesuv.infomalangbatu.menu.gallery.views.FragmentGallery
import com.yoesuv.infomalangbatu.menu.listplace.views.FragmentListPlace
import com.yoesuv.infomalangbatu.menu.other.views.FragmentOther
import com.yoesuv.infomalangbatu.utils.BottomNavigationViewHelper

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.main = viewModel

        setupToolbar()
        setupBottomNavigation()

        supportFragmentManager.beginTransaction().replace(R.id.containerMain, FragmentListPlace.getInstance()).commit()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupBottomNavigation(){
        binding.bottomNavigationViewMain.itemIconTintList = null
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigationViewMain)
        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener {
            when {
                it.itemId==R.id.menuList -> supportFragmentManager.beginTransaction().replace(R.id.containerMain, FragmentListPlace.getInstance()).commit()
                it.itemId==R.id.menuGallery -> supportFragmentManager.beginTransaction().replace(R.id.containerMain, FragmentGallery.getInstance()).commit()
                else -> supportFragmentManager.beginTransaction().replace(R.id.containerMain, FragmentOther.getInstance()).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}