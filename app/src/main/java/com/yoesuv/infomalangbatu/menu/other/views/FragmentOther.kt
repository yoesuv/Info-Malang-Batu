package com.yoesuv.infomalangbatu.menu.other.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentOtherBinding
import com.yoesuv.infomalangbatu.menu.other.adapters.NewTabOtherAdapter

class FragmentOther: Fragment() {

    private lateinit var binding:FragmentOtherBinding
    private lateinit var tabAdapter: NewTabOtherAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)

        setupAppBar(0F)
        setupViewPager()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        setupAppBar(8F)
    }

    private fun setupAppBar(elevation: Float){
        activity?.findViewById<AppBarLayout>(R.id.appBarMain)?.elevation = elevation
    }

    private fun setupViewPager() {
        val arrayTitle = requireContext().resources.getStringArray(R.array.array_tab_other)
        tabAdapter = NewTabOtherAdapter(requireActivity())
        tabAdapter.addFragment(ChildFragmentInfo.getInstance())
        tabAdapter.addFragment(ChildFragmentChangeLog.getInstance())
        tabAdapter.addFragment(ChildFragmentThanks.getInstance())
        tabAdapter.addFragment(ChildFragmentLibraries.getInstance())

        binding.viewPagerOther.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayoutViewPagerOther, binding.viewPagerOther) { tab, position ->
            tab.text = arrayTitle[position]
        }.attach()
    }

}