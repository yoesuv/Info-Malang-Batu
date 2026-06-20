package com.yoesuv.infomalangbatu.menu.maps.controllers

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.maps.adapters.MyCustomInfoWindowAdapter
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.BounceAnimation

/**
 * Loads map pins from the local database, renders markers with custom info
 * windows, and wires the bounce animation when a pin marker is tapped.
 */
class MapsPinController(
    private val appDbRepository: AppDbRepository?,
    private val viewLifecycleOwner: LifecycleOwner,
    private val activity: FragmentActivity?,
) {
    private var markerLocation: Marker? = null

    fun loadPins(googleMap: GoogleMap?) {
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
        appDbRepository?.selectAllMapPins()?.observe(viewLifecycleOwner) {
            setupPin(googleMap, it)
        }
    }

    private fun setupPin(
        googleMap: GoogleMap?,
        listPin: List<PinModel>,
    ) {
        if (listPin.isNotEmpty()) {
            for (pin in listPin) {
                val markerOptions = MarkerOptions()
                val pinLat = pin.latitude ?: 0.0
                val pinLng = pin.longitude ?: 0.0
                val pinName = pin.name ?: ""
                markerOptions.position(LatLng(pinLat, pinLng))
                markerOptions.title(pinName)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                markerLocation = googleMap?.addMarker(markerOptions)
                markerLocation?.tag = MarkerTag(pinName, 0, pinLat, pinLng)

                googleMap?.setInfoWindowAdapter(MyCustomInfoWindowAdapter(activity))
            }
        }
    }

    fun setupMarkerAnimation(googleMap: GoogleMap?) {
        googleMap?.setOnMarkerClickListener { marker ->
            val tag: MarkerTag = marker.tag as MarkerTag
            if (tag.type == 0) {
                val handler = Handler(Looper.getMainLooper())
                val anim = BounceAnimation(SystemClock.uptimeMillis(), 1000L, marker, handler)
                handler.post(anim)
                marker.showInfoWindow()
            } else {
                marker.hideInfoWindow()
            }
            return@setOnMarkerClickListener true
        }
    }
}
