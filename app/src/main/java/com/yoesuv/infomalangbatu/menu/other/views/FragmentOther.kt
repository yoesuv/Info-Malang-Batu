package com.yoesuv.infomalangbatu.menu.other.views

import androidx.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentOtherBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.TabOtherAdapter

class FragmentOther: Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return FragmentOther()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentOtherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)

        binding.viewPagerOther.adapter = TabOtherAdapter(childFragmentManager)
        binding.navigationTabStrip.setViewPager(binding.viewPagerOther)
        binding.navigationTabStrip.setTitles(getString(R.string.info), getString(R.string.changelog), getString(R.string.thanks_to), getString(R.string.library))
        binding.navigationTabStrip.inactiveColor = ContextCompat.getColor(context!!, R.color.grey_50)
        binding.navigationTabStrip.activeColor = Color.WHITE
        binding.navigationTabStrip.stripColor = ContextCompat.getColor(context!!, R.color.colorAccent)
        binding.navigationTabStrip.titleSize = 25F
        binding.navigationTabStrip.cornersRadius = 0F

        setupAppBar(0F)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        setupAppBar(8F)
    }

    private fun setupAppBar(elevation: Float){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            activity?.findViewById<AppBarLayout>(R.id.appBarMain)?.elevation = elevation
        }
    }

}