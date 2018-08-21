package com.yoesuv.infomalangbatu.menu.listplace.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel

class FragmentListPlace: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return FragmentListPlace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentListplaceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_listplace, container, false)
        val viewModel = ViewModelProviders.of(this).get(FragmentListPlaceViewModel::class.java)
        binding.listplace = viewModel
        return binding.root
    }

}