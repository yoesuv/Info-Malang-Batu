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
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.menu.maps.adapters.MyCustomInfoWindowAdapter
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.utils.BounceAnimation
import com.yoesuv.infomalangbatu.widgets.AppDialog
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class FragmentMaps : SupportMapFragment(), OnMapReadyCallback, DirectionCallback, MenuProvider {

    private var markerLocation: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var myLocationCallback: MyLocationCallback

    private var origin: LatLng? = null
    private var destination: LatLng? = null
    private val colors = arrayListOf("#7F2196f3", "#7F4CAF50", "#7FF44336")
    private lateinit var progressDialog: AppDialog

    private var appDatabase: AppDatabase? = null
    private var listPinModel: MutableList<PinModel> = arrayListOf()

    private val requestPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                enableUserLocation(mGoogleMap)
            }
        }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        appDatabase = AppDatabase.getInstance(requireContext())

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

    override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        if (direction?.isOK!!) {
            if (direction.routeList.size > 0) {
                mGoogleMap?.clear()
                mGoogleMap?.addMarker(
                    MarkerOptions().position(origin!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_origin))
                )?.tag = MarkerTag("Origin", 3, 0.0, 0.0)
                mGoogleMap?.addMarker(
                    MarkerOptions().position(destination!!)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                )?.tag = MarkerTag("Destination", 3, 0.0, 0.0)
                setCameraWithCoordinationBounds(direction.routeList[0])
                for (i: Int in 0 until direction.routeList.size) {
                    val color = colors[i % colors.size]
                    val route = direction.routeList[i]
                    val directionPositionList = route.legList[0].directionPoint
                    mGoogleMap?.addPolyline(
                        DirectionConverter.createPolyline(
                            context,
                            directionPositionList,
                            5,
                            Color.parseColor(color)
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

    override fun onDirectionFailure(t: Throwable?) {
        t?.printStackTrace()
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

        runBlocking {
            listPinModel.clear()
            appDatabase?.mapPinDaoAccess()?.selectAllDbMapPins()?.forEach { mapPinsRoom ->
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

    private fun setupPin(googleMap: GoogleMap?, listPin: MutableList<PinModel>) {
        if (listPin.isNotEmpty()) {
            for (pin in listPin) {
                val markerOptions = MarkerOptions()
                markerOptions.position(LatLng(pin.latitude!!, pin.longitude!!))
                markerOptions.title(pin.name)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                markerLocation = googleMap?.addMarker(markerOptions)
                markerLocation?.tag = MarkerTag(pin.name!!, 0, pin.latitude, pin.longitude)

                googleMap?.setInfoWindowAdapter(MyCustomInfoWindowAdapter(activity))
            }
        }
    }

    private fun setMarkerAnimation(googleMap: GoogleMap?) {
        googleMap?.setOnMarkerClickListener { marker ->
            val tag: MarkerTag = marker.tag as MarkerTag
            if (tag.type == 0) {
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

    @SuppressLint("MissingPermission")
    private fun enableUserLocation(googleMap: GoogleMap?) {
        myLocationCallback = MyLocationCallback()
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        val paddingBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 108F, resources.displayMetrics).roundToInt()
        googleMap?.setPadding(0, 0, 0, paddingBottom)
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 1000
        mFusedLocationProviderClient?.requestLocationUpdates(locationRequest, myLocationCallback, Looper.myLooper()!!)
    }

    private fun getDirection(marker: Marker?) {
        if (AppHelper.isPermissionLocationEnabled(requireContext())) {
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
                        view?.rootView?.let {
                            AppHelper.snackBarError(it, R.string.error_get_user_location)
                        }
                    }
                } else {
                    view?.rootView?.let {
                        AppHelper.snackBarError(it, R.string.error_get_user_location)
                    }
                }
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