package com.yoesuv.infomalangbatu.menu.other.views

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ChildFragmentInfoBinding

class ChildFragmentInfo : Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return ChildFragmentInfo()
        }
    }

    private var binding: ChildFragmentInfoBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.child_fragment_info, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.textViewVersion?.text = getString(R.string.info_app_version, BuildConfig.VERSION_NAME)
        binding?.textViewGithub?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context?.getString(R.string.link_github)))
            context?.startActivity(intent)
        }
    }

}