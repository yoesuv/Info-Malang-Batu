package com.yoesuv.infomalangbatu.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.data.AppConstants
import es.dmoral.toasty.Toasty

/**
 *  Created by yusuf on 5/1/18.
 */

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(AppConstants.TAG_DEBUG, message)
    }
}

object AppHelper {

    fun displayToastError(context: Context, message: String) {
        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show()
    }

    fun displayToastError(context: Context, @StringRes message: Int) {
        Toasty.error(context, context.getString(message), Toast.LENGTH_SHORT, true).show()
    }

    fun displayToastNormal(context: Context, message: String) {
        Toasty.normal(context, message, Toast.LENGTH_SHORT).show()
    }

    fun displayToastNormal(context: Context, @StringRes message: Int) {
        Toasty.normal(context, message, Toast.LENGTH_SHORT).show()
    }

    fun checkLocationSetting(context: Context): Boolean {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @Suppress("DEPRECATION")
    fun fromHtml(source: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(source).toString()
        }
    }

    fun isPermissionLocationEnabled(context: Context): Boolean {
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return permission == PackageManager.PERMISSION_GRANTED
    }

}
