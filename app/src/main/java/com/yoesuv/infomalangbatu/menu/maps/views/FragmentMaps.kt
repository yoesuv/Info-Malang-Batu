package com.yoesuv.infomalangbatu.menu.maps.views

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.maps.controllers.MapsDirectionController
import com.yoesuv.infomalangbatu.menu.maps.controllers.MapsLocationController
import com.yoesuv.infomalangbatu.menu.maps.controllers.MapsPinController
import com.yoesuv.infomalangbatu.utils.AppHelper

class FragmentMaps :
    SupportMapFragment(),
    OnMapReadyCallback,
    MenuProvider {
    private var mGoogleMap: GoogleMap? = null
    private var mapControlsBottomPadding = 0

    private var locationController: MapsLocationController? = null
    private var pinController: MapsPinController? = null
    private var directionController: MapsDirectionController? = null

    private val requestPermissionLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                locationController?.enableUserLocation(mGoogleMap, mapControlsBottomPadding)
            }
        }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        locationController = MapsLocationController(requireContext())
        directionController =
            MapsDirectionController(
                context = requireContext(),
                googleMapProvider = { mGoogleMap },
                rootViewProvider = { view?.rootView },
                onPermissionRequired = {
                    requestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
            )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupMapInsets(view)
        pinController =
            MapsPinController(
                appDbRepository = AppDbRepository(requireContext()),
                viewLifecycleOwner = viewLifecycleOwner,
                activity = activity,
            )
        getMapAsync(this)
    }

    private fun setupMapInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            mapControlsBottomPadding = systemBars.bottom
            mGoogleMap?.setPadding(0, 0, 0, mapControlsBottomPadding)
            windowInsets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationController?.removeLocationUpdates()
        directionController?.dismiss()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_map))
        googleMap.setPadding(0, 0, 0, mapControlsBottomPadding)
        pinController?.loadPins(googleMap)
        pinController?.setupMarkerAnimation(googleMap)

        if (AppHelper.checkLocationSetting(requireContext())) {
            requestPermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            directionController?.getDirection(marker)
        }
    }

    override fun onCreateMenu(
        menu: Menu,
        menuInflater: MenuInflater,
    ) {
        menuInflater.inflate(R.menu.menu_maps, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menuMapRefresh) {
            pinController?.loadPins(mGoogleMap)
        }
        return false
    }
}
