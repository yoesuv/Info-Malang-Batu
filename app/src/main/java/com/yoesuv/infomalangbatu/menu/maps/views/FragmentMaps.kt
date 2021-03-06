package com.yoesuv.infomalangbatu.menu.maps.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.room.Room
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
import com.yoesuv.infomalangbatu.App
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.menu.maps.adapters.MyCustomInfoWindowAdapter
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.BounceAnimation
import com.yoesuv.infomalangbatu.widgets.AppDialog
import kotlinx.coroutines.runBlocking

class FragmentMaps: SupportMapFragment(), OnMapReadyCallback, DirectionCallback {

    companion object {
        const val REQUEST_FEATURE_LOCATION_PERMISSION_CODE:Int = 12
        const val REQUEST_LOCATION_PERMISSION_CODE:Int = 14
    }

    private var markerLocation: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var myLocationCallback: MyLocationCallback? = null

    private var origin: LatLng? = null
    private var destination: LatLng? = null
    private val colors = arrayListOf("#7F2196f3","#7F4CAF50","#7FF44336")
    private lateinit var progressDialog: AppDialog

    private lateinit var appDatabase: AppDatabase
    private var listPinModel: MutableList<PinModel> = arrayListOf()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase::class.java, AppConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        setHasOptionsMenu(true)
        progressDialog = AppDialog(requireContext())
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myLocationCallback!=null) {
            LocationServices.getFusedLocationProviderClient(requireContext()).removeLocationUpdates(myLocationCallback)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_FEATURE_LOCATION_PERMISSION_CODE) {
            if (resultCode==Activity.RESULT_OK) {
                requestPermissionLocation()
            } else if (resultCode==Activity.RESULT_CANCELED) {
                AppHelper.displayToastError(requireContext(), getString(R.string.location_setting_off))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_maps, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menuMapRefresh){
            getMapPins(mGoogleMap)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults[0].equals(PackageManager.PERMISSION_GRANTED)) {
                enableUserLocation(mGoogleMap)
            } else {
                AppHelper.displayToastError(requireContext(), getString(R.string.access_location_denied))
            }
        }
    }

    private fun getMapPins(googleMap: GoogleMap?){
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))

        runBlocking {
            listPinModel.clear()
            appDatabase.mapPinDaoAccess().selectAllDbMapPins().forEach { mapPinsRoom ->
                val pinModel = PinModel(
                    mapPinsRoom.name,
                    mapPinsRoom.location,
                    mapPinsRoom.latitude,
                    mapPinsRoom.longitude
                )
                listPinModel.add(pinModel)
            }
            setupPin(googleMap, listPinModel)
        }
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
        googleMap?.setOnMarkerClickListener { marker ->
            val tag: MarkerTag = marker.tag as MarkerTag
            if (tag.type==0) {
                val handler = Handler()
                val anim = BounceAnimation(SystemClock.uptimeMillis(), 1000L, marker, handler)
                handler.post(anim)
                marker.showInfoWindow()
            } else {
                marker.hideInfoWindow()
            }
            return@setOnMarkerClickListener true
        }
    }

    private fun requestPermissionLocation(){
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
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

    private fun getDirection(marker: Marker?){
        if (AppHelper.isPermissionLocationEnabled(context)) {
            val tag: MarkerTag = marker?.tag as MarkerTag
            if (tag.type == 0) {
                if (!progressDialog.isShowing) {
                    progressDialog.show()
                }

                val latitude = App.prefHelper?.getString(AppConstants.PREFERENCE_LATITUDE)
                val longitude = App.prefHelper?.getString(AppConstants.PREFERENCE_LONGITUDE)
                origin = LatLng(latitude?.toDouble()!!, longitude?.toDouble()!!)
                destination = LatLng(tag.latitude, tag.longitude)

                if (latitude != "") {
                    if (longitude != "") {
                        GoogleDirection.withServerKey(context?.getString(R.string.DIRECTION_API_KEY))
                            .from(origin)
                            .to(destination)
                            .alternativeRoute(true)
                            .transportMode(TransportMode.DRIVING)
                            .avoid(AvoidType.TOLLS)
                            .execute(this)
                    } else {
                        AppHelper.displayToastError(requireContext(), getString(R.string.error_get_user_location))
                    }
                } else {
                    AppHelper.displayToastError(requireContext(), getString(R.string.error_get_user_location))
                }
            }
        } else {
            requestPermissionLocation()
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
        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))
        getMapPins(googleMap)
        setMarkerAnimation(googleMap)

        if (AppHelper.checkLocationSetting(requireContext())) {
            requestPermissionLocation()
        } else {
            AppHelper.displayLocationSettingsRequest(activity as Activity)
        }

        googleMap?.setOnInfoWindowClickListener { marker ->
            getDirection(marker)
        }
    }

    override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        if (direction?.isOK!!) {
            if (direction.routeList.size>0) {
                mGoogleMap?.clear()
                mGoogleMap?.addMarker(MarkerOptions().position(origin!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_origin)))?.tag = MarkerTag("Origin",3,0.0,0.0)
                mGoogleMap?.addMarker(MarkerOptions().position(destination!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)))?.tag = MarkerTag("Destination",3,0.0,0.0)
                setCameraWithCoordinationBounds(direction.routeList[0])
                for (i:Int in 0 until direction.routeList.size) {
                    val color = colors[i % colors.size]
                    val route = direction.routeList[i]
                    val directionPositionList = route.legList[0].directionPoint
                    mGoogleMap?.addPolyline(DirectionConverter.createPolyline(context, directionPositionList, 5, Color.parseColor(color)))
                }
            } else {
                AppHelper.displayToastError(requireContext(), context?.getString(R.string.error_direction_not_success)!!)
            }
        } else {
            AppHelper.displayToastError(requireContext(), context?.getString(R.string.error_direction_not_success)!!)
        }
    }

    override fun onDirectionFailure(t: Throwable?) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        AppHelper.displayToastError(requireContext(), context?.getString(R.string.error_direction_not_success)!!)
        t?.printStackTrace()
    }
}