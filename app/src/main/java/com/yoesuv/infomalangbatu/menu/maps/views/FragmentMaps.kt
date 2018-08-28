package com.yoesuv.infomalangbatu.menu.maps.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.maps.adapters.MyCustomInfoWindowAdapter
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.BounceAnimation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentMaps: SupportMapFragment(), OnMapReadyCallback {

    companion object {

        const val REQUEST_FEATURE_LOCATION_PERMISSION_CODE:Int = 12

        fun getInstance(): Fragment{
            return FragmentMaps()
        }
    }

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    private var markerLocation: Marker? = null
    private var mGoogleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_FEATURE_LOCATION_PERMISSION_CODE) {
            if (resultCode==Activity.RESULT_OK) {
                requestPermission(mGoogleMap)
            } else if (resultCode==Activity.RESULT_CANCELED) {
                AppHelper.displayToastError(context!!, getString(R.string.location_setting_off))
            }
        }
    }

    private fun getMapPins(googleMap: GoogleMap?){
        compositeDisposable.add(
                restApi.getMapPins()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            setupPin(googleMap, it)
                        },{
                            AppHelper.displayToastError(context?.applicationContext!!,getString(R.string.error_get_data_map_pins))
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
                markerLocation =  googleMap?.addMarker(markerOptions)
                markerLocation?.tag = MarkerTag(pin.name!!, 0, pin.latitude, pin.longitude)

                googleMap?.setInfoWindowAdapter(MyCustomInfoWindowAdapter(activity))
            }
        }
    }

    private fun setMarkerAnimation(googleMap: GoogleMap?){
        googleMap?.setOnMarkerClickListener {
            val handler = Handler()
            val anim = BounceAnimation(SystemClock.uptimeMillis(), 1000L, it, handler)
            handler.post(anim)
            it.showInfoWindow()
            return@setOnMarkerClickListener true
        }
    }

    private fun requestPermission(googleMap: GoogleMap?){
        val rxPermission = RxPermissions(activity as Activity)
        rxPermission.request(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe{ result: Boolean ->
                    if (result) {
                        enableUserLocation(googleMap)
                    } else {
                        AppHelper.displayToastError(context?.applicationContext!!, getString(R.string.access_location_denied))
                    }
                }
    }


    private fun enableUserLocation(googleMap: GoogleMap?){

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

        getMapPins(googleMap)
        setMarkerAnimation(googleMap)

        if (AppHelper.checkLocationSetting(context!!)) {
            requestPermission(googleMap)
        } else {
            AppHelper.displayLocationSettingsRequest(activity as Activity)
        }
    }
}