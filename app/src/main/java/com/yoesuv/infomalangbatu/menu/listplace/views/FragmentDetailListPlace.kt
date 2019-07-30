package com.yoesuv.infomalangbatu.menu.listplace.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentDetailListplaceBinding

class FragmentDetailListPlace: Fragment() {

    private lateinit var binding: FragmentDetailListplaceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_listplace, container, false)
        return binding.root
    }

}