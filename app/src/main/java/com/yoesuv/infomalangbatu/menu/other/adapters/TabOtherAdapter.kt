package com.yoesuv.infomalangbatu.menu.other.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentChangeLog
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentInfo
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentLibraries
import com.yoesuv.infomalangbatu.menu.other.views.ChildFragmentThanks

class TabOtherAdapter(context: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val arrayTitle = context?.resources?.getStringArray(R.array.array_tab_other)

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
        return arrayTitle!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayTitle?.get(position)
    }
}