package com.yoesuv.infomalangbatu.menu.other.views

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentThanksBinding
import com.yoesuv.infomalangbatu.utils.AppHelper

class ChildFragmentThanks: androidx.fragment.app.Fragment() {

    companion object {
        fun getInstance(): androidx.fragment.app.Fragment {
            return ChildFragmentThanks()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ChildFragmentThanksBinding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_thanks, container, false)
        binding.textViewThanks.text = AppHelper.fromHtml(getString(R.string.trims))
        return binding.root
    }

}