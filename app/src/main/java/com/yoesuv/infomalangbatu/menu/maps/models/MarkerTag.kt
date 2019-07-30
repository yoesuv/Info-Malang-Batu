package com.yoesuv.infomalangbatu.menu.maps.models

import androidx.annotation.Keep

@Keep
data class MarkerTag(val title:String, val type:Int, val latitude:Double, val longitude:Double)