package com.yoesuv.infomalangbatu.menu.other.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentOtherBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.TabOtherAdapter

class FragmentOther: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentOtherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)

        binding.viewPagerOther.adapter = TabOtherAdapter(requireContext(), childFragmentManager)
        binding.tabLayoutViewPagerOther.setupWithViewPager(binding.viewPagerOther)

        setupAppBar(0F)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        setupAppBar(8F)
    }

    private fun setupAppBar(elevation: Float){
        activity?.findViewById<AppBarLayout>(R.id.appBarMain)?.elevation = elevation
    }

}