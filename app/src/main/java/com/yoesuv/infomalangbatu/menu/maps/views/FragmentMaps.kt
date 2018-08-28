package com.yoesuv.infomalangbatu.menu.maps.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yoesuv.infomalangbatu.App
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

class FragmentMaps: SupportMapFragment(), OnMapReadyCallback, DirectionCallback {

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
    private val colors = arrayListOf("#7F2196f3","#7F4CAF50","#7FF44336")

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
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
        googleMap?.setPadding(0, 0, 0, 215)
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 1000
        mFusedLocationProviderClient?.requestLocationUpdates(locationRequest, myLocationCallback, Looper.myLooper())
    }

    private fun getDirection(marker: Marker?){
        val latitude = App.prefHelper?.getString(PREFERENCE_LATITUDE)
        val longitude = App.prefHelper?.getString(PREFERENCE_LONGITUDE)
        val tag: MarkerTag = marker?.tag as MarkerTag

        if (latitude!="") {
            if (longitude!="") {
                Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # destination location latitude : ${tag.latitude} /longitude : ${tag.longitude}")
                Log.d(AppConstants.TAG_DEBUG,"FragmentMaps # user location latitude : $latitude /longitude : $longitude")
                val origin = LatLng(latitude?.toDouble()!!, longitude?.toDouble()!!)
                val destination = LatLng(tag.latitude, tag.longitude)
                GoogleDirection.withServerKey(AppConstants.GOOGLE_MAPS_SERVER_KEY)
                        .from(origin).to(destination)
                        .alternativeRoute(true)
                        .transportMode(TransportMode.DRIVING)
                        .avoid(AvoidType.TOLLS)
                        .execute(this)
            }
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route){
        val southwest:LatLng = route.bound.southwestCoordination.coordination
        val northeast:LatLng = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

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

        googleMap?.setOnInfoWindowClickListener {
            getDirection(it)
        }
    }

    override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
        if (direction?.isOK!!) {
            if (direction.routeList.size>0) {
                mGoogleMap?.clear()
                setCameraWithCoordinationBounds(direction.routeList[0])
                for (i: Int in 0..(direction.routeList.size - 1)) {
                    val color = colors[i % colors.size]
                    val route = direction.routeList[i]
                    val directionPositionList = route.legList[0].directionPoint
                    mGoogleMap?.addPolyline(DirectionConverter.createPolyline(context, directionPositionList, 5, Color.parseColor(color)))
                }
            } else {
                AppHelper.displayToastError(context!!,"Tidak Mendapatkan Rute")
            }
        } else {
            AppHelper.displayToastError(context!!,"Gagal Mendapatkan Petunjuk Arah")
        }
    }

    override fun onDirectionFailure(t: Throwable?) {
        t?.printStackTrace()
        AppHelper.displayToastError(context!!,"Gagal Memproses Petunjuk Arah")
    }
}