package com.yoesuv.infomalangbatu.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlaceRoom (
    var name: String?,
    var location: String?,
    var description: String?,
    var thumbnail: String?,
    var image: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}