package com.yoesuv.infomalangbatu.menu.other.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentOtherBinding
import com.yoesuv.infomalangbatu.menu.other.viewmodels.FragmentOtherViewModel

class FragmentOther: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return FragmentOther()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentOtherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)
        val viewModel = ViewModelProviders.of(this).get(FragmentOtherViewModel::class.java)
        binding.other = viewModel
        return binding.root
    }

}