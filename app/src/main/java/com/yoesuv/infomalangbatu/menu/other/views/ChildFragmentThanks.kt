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

class ChildFragmentThanks : Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return ChildFragmentThanks()
        }
    }

    private var binding: ChildFragmentThanksBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_thanks, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.textViewThanks?.text = AppHelper.fromHtml(getString(R.string.trims))
    }

}