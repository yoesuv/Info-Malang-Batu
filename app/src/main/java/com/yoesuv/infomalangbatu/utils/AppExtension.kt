package com.yoesuv.infomalangbatu.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GenericTransitionOptions
import com.yoesuv.infomalangbatu.utils.glide.GlideApp


@BindingAdapter("setImageUrl")
fun AppCompatImageView.setImageUrl(imageUrl: String) {
    GlideApp.with(context)
        .load(imageUrl)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

