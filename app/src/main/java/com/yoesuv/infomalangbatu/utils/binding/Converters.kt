package com.yoesuv.infomalangbatu.utils.binding

import android.animation.Animator
import androidx.databinding.BindingAdapter
import androidx.appcompat.widget.AppCompatImageView
import android.view.View
import com.bumptech.glide.GenericTransitionOptions
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.utils.glide.GlideApp

class Converters {

    companion object {

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(imageView: AppCompatImageView, imageUrl:String){
            GlideApp.with(imageView.context.applicationContext)
                    .load(imageUrl)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("showView")
        fun showView(view: View, status: Boolean){
            if (status) {
                view.alpha = 0F
                view.animate().alpha(1F).duration = AppConstants.ANIM_DURATION
            }
        }

        @JvmStatic
        @BindingAdapter("hideView")
        fun hideView(view: View, status: Boolean){
            if (status) {
                view.visibility = View.VISIBLE
                view.alpha = 1F
            } else {
                view.animate().alpha(0F).setDuration(AppConstants.ANIM_DURATION).setListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        view.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {

                    }

                })
            }
        }

    }

}