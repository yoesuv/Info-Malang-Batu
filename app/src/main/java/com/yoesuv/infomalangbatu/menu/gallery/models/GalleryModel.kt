package com.yoesuv.infomalangbatu.menu.gallery.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class GalleryModel(
    @SerializedName("caption") @Expose val caption: String?,
    @SerializedName("thumbnail") @Expose val thumbnail: String?,
    @SerializedName("image") @Expose val image: String?
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}