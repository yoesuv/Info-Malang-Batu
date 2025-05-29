package com.yoesuv.infomalangbatu.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    fun fromHtml(source: String): String {
        return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    fun isPermissionLocationEnabled(context: Context): Boolean {
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    fun isVanillaIceCreamAndUp(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM
    }

    fun insetsPadding(
        view: View, left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false,
        @ColorInt color: Int? = null,
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                if (left) inset.left else v.paddingLeft,
                if (top) inset.top else v.paddingTop,
                if (right) inset.right else v.paddingRight,
                if (bottom) inset.bottom + 32 else v.paddingBottom
            )
            color?.let {
                v.setBackgroundColor(it)
            }
            windowInset
        }
    }

}
