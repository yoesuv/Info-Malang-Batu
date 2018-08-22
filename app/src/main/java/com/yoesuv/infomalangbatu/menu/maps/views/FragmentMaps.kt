package com.yoesuv.infomalangbatu.menu.maps.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants

class FragmentMaps: SupportMapFragment(), OnMapReadyCallback {

    companion object {
        fun getInstance(): Fragment{
            return FragmentMaps()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # onMapReady")
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))
    }
}