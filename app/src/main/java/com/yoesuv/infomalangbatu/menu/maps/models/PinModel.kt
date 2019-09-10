package com.yoesuv.infomalangbatu.menu.maps.models

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PinModel(
        @SerializedName("name") @Expose val name: String?,
        @SerializedName("lokasi") @Expose val location: Int?,
        @SerializedName("latitude") @Expose val latitude: Double?,
        @SerializedName("longitude") @Expose val longitude: Double?
)