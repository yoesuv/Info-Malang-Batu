package com.yoesuv.infomalangbatu.menu.maps.adapters

import android.view.ContextThemeWrapper
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.android.material.textview.MaterialTextView
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag

class MyCustomInfoWindowAdapter(activity: FragmentActivity?) : InfoWindowAdapter {

    private val ctxWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
    private val mContents: View = LayoutInflater.from(ctxWrapper)
        .inflate(R.layout.custom_info_window, activity?.findViewById(android.R.id.content), false)

    override fun getInfoContents(marker: Marker): View {
        return mContents
    }

    override fun getInfoWindow(marker: Marker): View {
        val tag = marker.tag as MarkerTag
        if (tag.type == 0) {
            val tv: MaterialTextView? = mContents.findViewById(R.id.textViewMapLocationName)
            tv?.text = tag.title
        }
        return mContents
    }
}