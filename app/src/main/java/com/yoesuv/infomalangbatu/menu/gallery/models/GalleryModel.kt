package com.yoesuv.infomalangbatu.menu.gallery.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class GalleryModel(
        @SerializedName("caption") @Expose val caption: String?,
        @SerializedName("thumbnail") @Expose val thumbnail: String?,
        @SerializedName("image") @Expose val image: String?
) : Parcelable