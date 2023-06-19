package com.yoesuv.infomalangbatu.menu.listplace.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class PlaceModel(
    @SerializedName("nama") @Expose val name: String?,
    @SerializedName("lokasi") @Expose val location: String?,
    @SerializedName("kategori") @Expose val category: String?,
    @SerializedName("deskripsi") @Expose val description: String?,
    @SerializedName("thumbnail") @Expose val thumbnail: String?,
    @SerializedName("gambar") @Expose val image: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}