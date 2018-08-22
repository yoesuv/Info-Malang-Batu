package com.yoesuv.infomalangbatu.menu.other.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R

class ChildFragmentChangeLog: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentChangeLog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.child_fragment_changelog, container, false)
    }

}