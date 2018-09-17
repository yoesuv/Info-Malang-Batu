package com.yoesuv.infomalangbatu.menu.maps.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
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
        const val PREFERENCE_LATITUDE = "preference_latitude"
        const val PREFERENCE_LONGITUDE = "preference_longitude"

        fun getInstance(): Fragment{
            return FragmentMaps()
        }
    }

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    private var markerLocation: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var myLocationCallback: MyLocationCallback? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        if (myLocationCallback!=null) {
            LocationServices.getFusedLocationProviderClient(context!!).removeLocationUpdates(myLocationCallback)
        }
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_maps, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId==R.id.menuMapRefresh){
            getMapPins(mGoogleMap)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMapPins(googleMap: GoogleMap?){
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
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
                    Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # requestPermission is $result")
                    if (result) {
                        enableUserLocation(googleMap)
                    } else {
                        AppHelper.displayToastError(context?.applicationContext!!, getString(R.string.access_location_denied))
                    }
                }
    }


    @SuppressLint("MissingPermission")
    private fun enableUserLocation(googleMap: GoogleMap?){
        myLocationCallback = MyLocationCallback(googleMap)
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        val paddingBottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 108F, resources.displayMetrics))
        googleMap?.setPadding(0, 0, 0, paddingBottom)
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 1000
        mFusedLocationProviderClient?.requestLocationUpdates(locationRequest, myLocationCallback, Looper.myLooper())
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap
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