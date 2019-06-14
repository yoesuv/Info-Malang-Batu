package com.yoesuv.infomalangbatu.utils

import android.app.Activity
import com.yoesuv.infomalangbatu.R
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.menu.maps.views.FragmentMaps
import es.dmoral.toasty.Toasty

/**
 *  Created by yusuf on 5/1/18.
 */

object AppHelper {

    fun displayToastError(context: Context, message: String){
        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show()
    }

    fun displayToastNormal(context: Context, message: String){
        Toasty.normal(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getToolbarHeight(context: Context): Int {
        val styledAttributes = context.theme.obtainStyledAttributes(
                intArrayOf(R.attr.actionBarSize))
        val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()

        return toolbarHeight
    }

    fun checkLocationSetting(context: Context):Boolean{
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @Suppress("DEPRECATION")
    fun fromHtml(source: String): String{
        return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            Html.fromHtml(source).toString()
        }
    }

    fun displayLocationSettingsRequest(activity: Activity){
        val googleApiClient = GoogleApiClient.Builder(activity.applicationContext).addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 10000/2

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                val response: LocationSettingsResponse? = task.getResult(ApiException::class.java)
            }catch (ex: ApiException) {
                if(ex.statusCode== LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                    val resolvableApiException = ex as ResolvableApiException
                    try{
                        resolvableApiException.startResolutionForResult(activity, FragmentMaps.REQUEST_FEATURE_LOCATION_PERMISSION_CODE)
                    }catch (e: IntentSender.SendIntentException){
                        Log.e(AppConstants.TAG_ERROR, "AppHelper # displayLocationSettingsRequest -> RESOLUTION_REQUIRED ${e.message}")
                    }
                }else if(ex.statusCode== LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                    Log.e(AppConstants.TAG_ERROR, "AppHelper # displayLocationSettingsRequest -> LocationSettings DISABLED")
                }
            }
        }
    }

}
