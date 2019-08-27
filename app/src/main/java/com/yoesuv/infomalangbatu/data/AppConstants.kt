package com.yoesuv.infomalangbatu.data

import com.yoesuv.infomalangbatu.BuildConfig

object AppConstants {

    const val TAG_DEBUG = "result_debug"
    const val TAG_ERROR = "result_error"
    const val ANIM_DURATION = 300L
    const val CONNECTION_TIME_OUT = 30 * 1000L
    const val PREFERENCE_NAME = "com.yoesuv.infomalangbatu.pref"

    const val PREFERENCE_LATITUDE = "preference_latitude"
    const val PREFERENCE_LONGITUDE = "preference_longitude"

    const val DATABASE_NAME = "info_malang_batu_database_{${BuildConfig.VERSION_NAME}}"

}