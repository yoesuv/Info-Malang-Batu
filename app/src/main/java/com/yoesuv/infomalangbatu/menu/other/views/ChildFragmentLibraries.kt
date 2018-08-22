package com.yoesuv.infomalangbatu.menu.other.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R

class ChildFragmentLibraries: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentLibraries()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.child_fragment_libraries, container, false)
    }

}