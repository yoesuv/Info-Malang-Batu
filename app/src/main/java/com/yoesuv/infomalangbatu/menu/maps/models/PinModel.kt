package com.yoesuv.infomalangbatu.menu.maps.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Entity
data class PinModel(
    @SerializedName("name") @Expose val name: String?,
    @SerializedName("lokasi") @Expose val location: Int?,
    @SerializedName("latitude") @Expose val latitude: Double?,
    @SerializedName("longitude") @Expose val longitude: Double?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}