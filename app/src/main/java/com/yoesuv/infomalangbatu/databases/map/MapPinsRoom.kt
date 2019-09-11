package com.yoesuv.infomalangbatu.databases.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MapPinsRoom (
    var name: String?,
    var location: Int?,
    var latitude: Double?,
    var longitude: Double?
){
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}