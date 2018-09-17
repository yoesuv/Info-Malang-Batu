package com.yoesuv.infomalangbatu.menu.other.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentChangeLog
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentInfo
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentLibraries
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentThanks

class TabOtherAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ChildFragmentInfo.getInstance()
            1 -> ChildFragmentChangeLog.getInstance()
            2 -> ChildFragmentThanks.getInstance()
            3 -> ChildFragmentLibraries.getInstance()
            else -> ChildFragmentInfo.getInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}