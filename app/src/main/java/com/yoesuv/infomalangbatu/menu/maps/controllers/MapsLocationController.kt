package com.yoesuv.infomalangbatu.menu.maps.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMap
import com.yoesuv.infomalangbatu.menu.maps.views.MyLocationCallback

/**
 * Encapsulates runtime location permission handling and fused location
 * updates for the map. Holds no View references besides the [GoogleMap]
 * passed into [enableUserLocation], which is owned by the fragment.
 */
class MapsLocationController(
    context: Context,
) {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var myLocationCallback: MyLocationCallback

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    fun enableUserLocation(
        googleMap: GoogleMap?,
        bottomPadding: Int,
    ) {
        myLocationCallback = MyLocationCallback()
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.setPadding(0, 0, 0, bottomPadding)
        val locationRequest =
            LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
                .setWaitForAccurateLocation(false)
                .build()
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            myLocationCallback,
            Looper.getMainLooper(),
        )
    }

    fun removeLocationUpdates() {
        if (::myLocationCallback.isInitialized) {
            fusedLocationProviderClient?.removeLocationUpdates(myLocationCallback)
        }
    }
}
