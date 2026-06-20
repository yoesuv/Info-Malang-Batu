package com.yoesuv.infomalangbatu.main.views

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivityMainBinding
import com.yoesuv.infomalangbatu.main.viewmodels.MainViewModel
import com.yoesuv.infomalangbatu.utils.AppHelper

class MainActivity : AppCompatActivity() {
    companion object {
        var backPressed: Long = 0L
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.main = viewModel

        setupToolbar()
        binding.bottomNavigationViewMain.itemIconTintList = null
        setupNavigation()
        setupOnBackPressed()
        setupFragmentContainerInsets()

        AppHelper.insetsPadding(binding.coordinatorLayoutMain, top = true, color = ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentMain) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationViewMain, navController)
    }

    fun hideNavigation(value: Boolean) {
        binding.bottomNavigationViewMain.visibility = if (value) View.GONE else View.VISIBLE
        ViewCompat.requestApplyInsets(binding.fragmentMain)
    }

    private fun setupFragmentContainerInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentMain) { view, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemBars.bottom)
            windowInsets
        }

        binding.bottomNavigationViewMain.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            ViewCompat.requestApplyInsets(binding.fragmentMain)
        }
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentDestination?.id == R.id.fragmentList) {
                        if ((backPressed + 2000L) > System.currentTimeMillis()) {
                            finish()
                        } else {
                            AppHelper.snackBarWarning(binding.coordinatorLayoutMain.rootView, R.string.confirm_close)
                        }
                        backPressed = System.currentTimeMillis()
                    } else {
                        navController.popBackStack()
                    }
                }
            },
        )
    }
}
