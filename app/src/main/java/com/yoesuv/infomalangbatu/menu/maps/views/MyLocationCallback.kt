package com.yoesuv.infomalangbatu.menu.maps.views

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.yoesuv.infomalangbatu.App
import com.yoesuv.infomalangbatu.data.AppConstants

class MyLocationCallback(private val googleMap: GoogleMap?): LocationCallback() {

    override fun onLocationResult(result: LocationResult) {
        super.onLocationResult(result)
        val listLocation = result.locations
        if (listLocation.isNotEmpty()) {
            App.prefHelper?.setString(AppConstants.PREFERENCE_LATITUDE, listLocation[0].latitude.toString())
            App.prefHelper?.setString(AppConstants.PREFERENCE_LONGITUDE, listLocation[0].longitude.toString())
        }
    }

}