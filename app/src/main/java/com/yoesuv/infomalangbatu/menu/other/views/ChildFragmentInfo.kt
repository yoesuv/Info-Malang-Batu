package com.yoesuv.infomalangbatu.menu.other.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R

class ChildFragmentInfo: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.child_fragment_info, container, false)
        return view
    }

}