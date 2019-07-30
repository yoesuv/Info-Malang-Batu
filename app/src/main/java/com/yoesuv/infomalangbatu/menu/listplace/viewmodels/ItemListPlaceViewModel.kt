package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class ItemListPlaceViewModel(placeModel: PlaceModel): ViewModel() {

    var title: ObservableField<String> = ObservableField(placeModel.name!!)
    var location: ObservableField<String> = ObservableField(placeModel.location!!)
    var imageUrl: ObservableField<String> = ObservableField(placeModel.image!!)

}