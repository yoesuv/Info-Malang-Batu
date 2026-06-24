package com.yoesuv.infomalangbatu.menu.other.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NewTabOtherAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment = mFragmentList[position]
}
