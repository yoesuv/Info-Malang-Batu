package com.yoesuv.infomalangbatu.menu.maps.views

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.TypedValue
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
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
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.yoesuv.infomalangbatu.App
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.maps.adapters.MyCustomInfoWindowAdapter
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.BounceAnimation
import com.yoesuv.infomalangbatu.widgets.AppDialog
import kotlin.math.roundToInt
import androidx.core.graphics.toColorInt

class FragmentMaps : SupportMapFragment(), OnMapReadyCallback, DirectionCallback, MenuProvider {

    private var markerLocation: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var myLocationCallback: MyLocationCallback

    private var origin: LatLng? = null
    private var destination: LatLng? = null
    private val colors = arrayListOf("#7F2196f3", "#7F4CAF50", "#7FF44336")
    private lateinit var progressDialog: AppDialog

    private var appDbRepository: AppDbRepository? = null

    private val requestPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                enableUserLocation(mGoogleMap)
            }
        }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        appDbRepository = AppDbRepository(requireContext())

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        progressDialog = AppDialog(requireContext())
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationServices.getFusedLocationProviderClient(requireContext()).removeLocationUpdates(myLocationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_map))
        getMapPins(googleMap)
        setMarkerAnimation(googleMap)

        if (AppHelper.checkLocationSetting(requireContext())) {
            requestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            getDirection(marker)
        }
    }

    override fun onDirectionSuccess(direction: Direction?) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        val isOk = direction?.isOK ?: false
        val routeList = direction?.routeList ?: emptyList()
        if (isOk) {
            if (routeList.isNotEmpty()) {
                mGoogleMap?.clear()
                mGoogleMap?.addMarker(
                    MarkerOptions().position(origin!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_origin))
                )?.tag = MarkerTag("Origin", 3, 0.0, 0.0)
                mGoogleMap?.addMarker(
                    MarkerOptions().position(destination!!)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                )?.tag = MarkerTag("Destination", 3, 0.0, 0.0)
                setCameraWithCoordinationBounds(routeList[0])
                for (i: Int in routeList.indices) {
                    val color = colors[i % colors.size]
                    val route = routeList[i]
                    val directionPositionList = route.legList[0].directionPoint
                    mGoogleMap?.addPolyline(
                        DirectionConverter.createPolyline(
                            requireContext(),
                            directionPositionList,
                            5,
                            color.toColorInt()
                        )
                    )
                }
            } else {
                view?.rootView?.let {
                    AppHelper.snackBarError(it, R.string.error_direction_not_success)
                }
            }
        } else {
            view?.rootView?.let {
                AppHelper.snackBarError(it, R.string.error_direction_not_success)
            }
        }
    }


    override fun onDirectionFailure(t: Throwable) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        view?.rootView?.let {
            AppHelper.snackBarError(it, R.string.error_direction_not_success)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_maps, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menuMapRefresh) {
            getMapPins(mGoogleMap)
        }
        return false
    }

    private fun getMapPins(googleMap: GoogleMap?) {
        googleMap?.clear()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.982914, 112.630875)))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(9F))
        appDbRepository?.selectAllMapPins()?.observe(viewLifecycleOwner) {
            setupPin(googleMap, it)
        }
    }

    private fun setupPin(googleMap: GoogleMap?, listPin: List<PinModel>) {
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

    private fun setMarkerAnimation(googleMap: GoogleMap?) {
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

    @SuppressLint("MissingPermission")
    private fun enableUserLocation(googleMap: GoogleMap?) {
        myLocationCallback = MyLocationCallback()
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        val paddingBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150F, resources.displayMetrics).roundToInt()
        googleMap?.setPadding(0, 0, 0, paddingBottom)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setWaitForAccurateLocation(false)
            .build()
        mFusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            myLocationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getDirection(marker: Marker?) {
        if (AppHelper.isPermissionLocationEnabled(requireContext())) {
            val tag: MarkerTag = marker?.tag as MarkerTag
            if (tag.type == 0) {
                if (!progressDialog.isShowing) {
                    progressDialog.show()
                }

                val latitude = App.prefHelper?.getDouble(AppConstants.PREFERENCE_LATITUDE) ?: 0.0
                val longitude = App.prefHelper?.getDouble(AppConstants.PREFERENCE_LONGITUDE) ?: 0.0
                origin = LatLng(latitude, longitude)
                destination = LatLng(tag.latitude, tag.longitude)

                val apiKey = requireContext().getString(R.string.DIRECTION_API_KEY)
                GoogleDirection.withServerKey(apiKey)
                    .from(origin!!)
                    .to(destination!!)
                    .alternativeRoute(true)
                    .transportMode(TransportMode.DRIVING)
                    .avoid(AvoidType.TOLLS)
                    .execute(this)
            }
        } else {
            requestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest: LatLng = route.bound.southwestCoordination.coordination
        val northeast: LatLng = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}