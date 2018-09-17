package com.yoesuv.infomalangbatu.menu.other.views

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentInfoBinding

class ChildFragmentInfo: Fragment() {

    companion object {
        fun getInstance(): Fragment{
            return ChildFragmentInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ChildFragmentInfoBinding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_info, container, false)
        binding.textViewVersion.text = getString(R.string.info_app_version, BuildConfig.VERSION_NAME)
        binding.textViewGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context?.getString(R.string.link_github)))
            context?.startActivity(intent)
        }
        return binding.root
    }

}