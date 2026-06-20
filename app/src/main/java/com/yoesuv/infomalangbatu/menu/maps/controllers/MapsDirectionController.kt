package com.yoesuv.infomalangbatu.menu.maps.controllers

import android.content.Context
import android.view.View
import androidx.core.graphics.toColorInt
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yoesuv.infomalangbatu.App
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.maps.models.MarkerTag
import com.yoesuv.infomalangbatu.utils.AppHelper
import com.yoesuv.infomalangbatu.widgets.AppDialog

/**
 * Owns Google Directions API requests and the resulting polyline/camera
 * rendering. Implements [DirectionCallback] so the fragment no longer has to.
 * The map and root view are accessed lazily via providers to avoid holding
 * references that outlive the fragment's view.
 */
class MapsDirectionController(
    private val context: Context,
    private val googleMapProvider: () -> GoogleMap?,
    private val rootViewProvider: () -> View?,
    private val onPermissionRequired: () -> Unit,
) : DirectionCallback {
    private var origin: LatLng? = null
    private var destination: LatLng? = null
    private val colors = arrayListOf("#7F2196f3", "#7F4CAF50", "#7FF44336")

    private val progressDialog: AppDialog =
        AppDialog(context).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

    fun getDirection(marker: Marker?) {
        if (AppHelper.isPermissionLocationEnabled(context)) {
            val tag: MarkerTag = marker?.tag as MarkerTag
            if (tag.type == 0) {
                if (!progressDialog.isShowing) {
                    progressDialog.show()
                }

                val latitude = App.prefHelper?.getDouble(AppConstants.PREFERENCE_LATITUDE) ?: 0.0
                val longitude = App.prefHelper?.getDouble(AppConstants.PREFERENCE_LONGITUDE) ?: 0.0
                origin = LatLng(latitude, longitude)
                destination = LatLng(tag.latitude, tag.longitude)

                val apiKey = context.getString(R.string.DIRECTION_API_KEY)
                GoogleDirection
                    .withServerKey(apiKey)
                    .from(origin!!)
                    .to(destination!!)
                    .alternativeRoute(true)
                    .transportMode(TransportMode.DRIVING)
                    .avoid(AvoidType.TOLLS)
                    .execute(this)
            }
        } else {
            onPermissionRequired()
        }
    }

    override fun onDirectionSuccess(direction: Direction?) {
        dismissProgressDialog()
        val isOk = direction?.isOK ?: false
        val routeList = direction?.routeList ?: emptyList()
        if (isOk && routeList.isNotEmpty()) {
            drawRoute(routeList)
        } else {
            showError()
        }
    }

    override fun onDirectionFailure(t: Throwable) {
        dismissProgressDialog()
        showError()
    }

    fun dismiss() {
        dismissProgressDialog()
    }

    private fun drawRoute(routeList: List<Route>) {
        val googleMap = googleMapProvider()
        googleMap?.clear()
        googleMap
            ?.addMarker(
                MarkerOptions()
                    .position(origin!!)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_origin)),
            )?.tag = MarkerTag("Origin", 3, 0.0, 0.0)
        googleMap
            ?.addMarker(
                MarkerOptions()
                    .position(destination!!)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)),
            )?.tag = MarkerTag("Destination", 3, 0.0, 0.0)
        setCameraWithCoordinationBounds(routeList[0])
        for (i: Int in routeList.indices) {
            val color = colors[i % colors.size]
            val route = routeList[i]
            val directionPositionList = route.legList[0].directionPoint
            googleMap?.addPolyline(
                DirectionConverter.createPolyline(
                    context,
                    directionPositionList,
                    5,
                    color.toColorInt(),
                ),
            )
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest: LatLng = route.bound.southwestCoordination.coordination
        val northeast: LatLng = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        googleMapProvider()?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    private fun showError() {
        rootViewProvider()?.let {
            AppHelper.snackBarError(it, R.string.error_direction_not_success)
        }
    }

    private fun dismissProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}
