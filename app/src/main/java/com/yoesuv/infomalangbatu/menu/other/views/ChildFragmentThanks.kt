package com.yoesuv.infomalangbatu.menu.other.views

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentThanksBinding
import com.yoesuv.infomalangbatu.utils.AppHelper

class ChildFragmentThanks: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentThanks()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ChildFragmentThanksBinding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_thanks, container, false)
        binding.textViewThanks.text = AppHelper.fromHtml(getString(R.string.trims))
        return binding.root
    }

}