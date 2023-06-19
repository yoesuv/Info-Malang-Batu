package com.yoesuv.infomalangbatu.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants

/**
 *  Created by yusuf on 5/1/18.
 */

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(AppConstants.TAG_DEBUG, message)
    }
}

object AppHelper {

    fun snackBarWarning(view: View, @StringRes message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context, R.color.amber_600))
            .show()
    }

    fun snackBarError(view: View, @StringRes message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context, R.color.red_700))
            .show()
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
