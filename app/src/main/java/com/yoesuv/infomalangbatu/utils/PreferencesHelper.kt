package com.yoesuv.infomalangbatu.utils

import android.content.Context
import android.content.SharedPreferences
import com.yoesuv.infomalangbatu.data.AppConstants

class PreferencesHelper(context: Context) {

    private val prefHelper: SharedPreferences =
        context.getSharedPreferences(AppConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setDouble(key: String, value: Double) {
        prefHelper.edit().putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()
    }

    fun getDouble(key: String): Double {
        val value = prefHelper.getLong(key, 0L)
        return java.lang.Double.longBitsToDouble(value)
    }
}