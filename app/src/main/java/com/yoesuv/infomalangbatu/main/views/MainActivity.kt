package com.yoesuv.infomalangbatu.main.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivityMainBinding
import com.yoesuv.infomalangbatu.main.viewmodels.MainViewModel
import com.yoesuv.infomalangbatu.utils.AppHelper

class MainActivity : AppCompatActivity() {

    companion object {
        var BACK_PRESSED: Long = 0L
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.main = viewModel

        setupToolbar()
        binding.bottomNavigationViewMain.itemIconTintList = null
        setupNavigation()
        setupOnBackPressed()
    }


    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentMain).navigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentMain) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationViewMain, navController)
    }

    fun hideNavigation(value: Boolean) {
        if (value) {
            binding.bottomNavigationViewMain.visibility = View.GONE
        } else {
            binding.bottomNavigationViewMain.visibility = View.VISIBLE
        }
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.fragmentMain)
                if (nav.currentDestination?.id == R.id.fragmentList) {
                    if ((BACK_PRESSED + 2000L) > System.currentTimeMillis()) {
                        onBackPressedDispatcher.onBackPressed()
                    } else {
                        AppHelper.displayToastNormal(this@MainActivity, R.string.confirm_close)
                    }
                    BACK_PRESSED = System.currentTimeMillis()
                } else {
                    nav.popBackStack()
                }
            }
        })
    }

}