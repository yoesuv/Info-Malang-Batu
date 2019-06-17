package com.yoesuv.infomalangbatu.menu.maps.adapters

import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import kotlinx.android.synthetic.main.custom_info_window.view.*

class MyCustomInfoWindowAdapter(activity: FragmentActivity?): GoogleMap.InfoWindowAdapter {

    private val mContents: View = LayoutInflater.from(activity?.applicationContext).inflate(R.layout.custom_info_window, null)

    override fun getInfoContents(marker: Marker?): View {
        return mContents
    }


    override fun getInfoWindow(marker: Marker?): View {
        val tag: MarkerTag = marker?.tag as MarkerTag
        if (tag.type==0) {
            mContents.textViewMapLocationName.text = tag.title
        }
        return mContents
    }
}