package com.yoesuv.infomalangbatu.menu.other.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentThanksBinding
import com.yoesuv.infomalangbatu.utils.AppHelper

class ChildFragmentThanks : Fragment() {
    companion object {
        fun getInstance(): Fragment = ChildFragmentThanks()
    }

    private var binding: ChildFragmentThanksBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_thanks, container, false)
        }
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.textViewThanks?.text = AppHelper.fromHtml(getString(R.string.trims))
    }
}
