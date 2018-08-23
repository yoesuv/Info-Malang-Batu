package com.yoesuv.infomalangbatu.menu.other.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentOtherBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.TabOtherAdapter
import com.yoesuv.infomalangbatu.menu.other.viewmodels.FragmentOtherViewModel

class FragmentOther: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return FragmentOther()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentOtherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)
        val viewModel = ViewModelProviders.of(this).get(FragmentOtherViewModel::class.java)
        binding.other = viewModel

        binding.viewPagerOther.adapter = TabOtherAdapter(childFragmentManager)
        binding.navigationTabStrip.setViewPager(binding.viewPagerOther)
        binding.navigationTabStrip.setTitles(getString(R.string.info), getString(R.string.changelog), getString(R.string.thanks_to), getString(R.string.library))
        binding.navigationTabStrip.inactiveColor = ContextCompat.getColor(context!!, R.color.grey_50)
        binding.navigationTabStrip.activeColor = Color.WHITE
        binding.navigationTabStrip.stripColor = ContextCompat.getColor(context!!, R.color.colorAccent)
        binding.navigationTabStrip.titleSize = 25F
        binding.navigationTabStrip.cornersRadius = 0F

        return binding.root
    }

}