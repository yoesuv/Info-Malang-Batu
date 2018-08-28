package com.yoesuv.infomalangbatu.menu.maps.views

import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.yoesuv.infomalangbatu.data.AppConstants

class MyLocationCallback(private val googleMap: GoogleMap?): LocationCallback() {

    override fun onLocationResult(result: LocationResult?) {
        super.onLocationResult(result)
        val listLocation = result?.locations
        if (listLocation?.isNotEmpty()!!) {
            Log.d(AppConstants.TAG_DEBUG,"MyLocationCallback # result ${listLocation.size} location")
        }
    }

}