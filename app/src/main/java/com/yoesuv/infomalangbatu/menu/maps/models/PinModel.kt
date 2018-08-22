package com.yoesuv.infomalangbatu.menu.maps.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PinModel(
        @SerializedName("name") @Expose val name: String?,
        @SerializedName("lokasi") @Expose val location: String?,
        @SerializedName("latitude") @Expose val latitude: Double?,
        @SerializedName("longitude") @Expose val longitude: Double?
)