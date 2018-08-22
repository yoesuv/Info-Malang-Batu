package com.yoesuv.infomalangbatu.menu.maps.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentMaps: SupportMapFragment(), OnMapReadyCallback {

    companion object {
        fun getInstance(): Fragment{
            return FragmentMaps()
        }
    }

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun getMapPins(googleMap: GoogleMap?){
        compositeDisposable.add(
                restApi.getMapPins()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # map pin size ${it.size}")
                            setupPin(googleMap, it)
                        },{

                        })
        )
    }

    private fun setupPin(googleMap: GoogleMap?, listPin: MutableList<PinModel>){
        if (listPin.isNotEmpty()) {
            for (pin in listPin){
                val markerOptions = MarkerOptions()
                markerOptions.position(LatLng(pin.latitude!!, pin.longitude!!))
                markerOptions.title(pin.name)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                googleMap?.addMarker(markerOptions)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # onMapReady")
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

        getMapPins(googleMap)
    }
}