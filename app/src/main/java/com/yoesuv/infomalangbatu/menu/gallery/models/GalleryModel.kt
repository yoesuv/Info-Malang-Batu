package com.yoesuv.infomalangbatu.menu.gallery.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GalleryModel(
        @SerializedName("caption") @Expose val caption: String?,
        @SerializedName("thumbnail") @Expose val thumbnail: String?,
        @SerializedName("image") @Expose val image: String?
)