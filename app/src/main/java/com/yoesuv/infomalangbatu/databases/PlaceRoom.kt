package com.yoesuv.infomalangbatu.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlaceRoom {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var name: String? = null
    var location: String? = null
    var description: String? = null
    var thumbnail: String? = null
    var image: String? = null
}