package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class FragmentDetailListPlaceViewModel(placeModel: PlaceModel?) : ViewModel() {
    var title: ObservableField<String> = ObservableField(placeModel?.name!!)
    var description: ObservableField<String> = ObservableField(placeModel?.description!!)
    var imageUrl: ObservableField<String> = ObservableField(placeModel?.image!!)
}