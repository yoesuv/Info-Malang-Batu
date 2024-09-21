package com.yoesuv.infomalangbatu.menu.maps.views

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.yoesuv.infomalangbatu.App
import com.yoesuv.infomalangbatu.data.AppConstants

class MyLocationCallback: LocationCallback() {

    override fun onLocationResult(result: LocationResult) {
        super.onLocationResult(result)
        val listLocation = result.locations
        if (listLocation.isNotEmpty()) {
            App.prefHelper?.let { pref ->
                pref.setDouble(AppConstants.PREFERENCE_LATITUDE, listLocation[0].latitude)
                pref.setDouble(AppConstants.PREFERENCE_LONGITUDE, listLocation[0].longitude)
            }
        }
    }

}