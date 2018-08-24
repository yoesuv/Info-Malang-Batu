package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class DetailListPlaceViewModel(placeModel: PlaceModel?, application: Application) : AndroidViewModel(application) {

    var title: ObservableField<String> = ObservableField(placeModel?.name!!)
    var description: ObservableField<String> = ObservableField(placeModel?.description!!)
    var imageUrl: ObservableField<String> = ObservableField(placeModel?.image!!)

}