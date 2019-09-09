package com.yoesuv.infomalangbatu.databases.gallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GaleriRoom (
    var caption: String?,
    var thumbnail: String?,
    var image: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}