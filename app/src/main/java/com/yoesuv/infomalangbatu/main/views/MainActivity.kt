package com.yoesuv.infomalangbatu.main.views

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivityMainBinding
import com.yoesuv.infomalangbatu.main.viewmodels.MainViewModel
import com.yoesuv.infomalangbatu.menu.gallery.views.FragmentGallery
import com.yoesuv.infomalangbatu.menu.listplace.views.FragmentListPlace
import com.yoesuv.infomalangbatu.menu.maps.views.FragmentMaps
import com.yoesuv.infomalangbatu.menu.other.views.FragmentOther
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    companion object {
        var BACK_PRESSED: Long = 0L
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.main = viewModel

        setupToolbar()
        setupNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for(fragment in supportFragmentManager.fragments){
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        if ((BACK_PRESSED+2000L) > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            AppHelper.displayToastNormal(this, getString(R.string.confirm_close))
        }
        BACK_PRESSED = System.currentTimeMillis()
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentMain).navigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.fragmentMain)
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(bottomNavigationViewMain, navController)
    }

}