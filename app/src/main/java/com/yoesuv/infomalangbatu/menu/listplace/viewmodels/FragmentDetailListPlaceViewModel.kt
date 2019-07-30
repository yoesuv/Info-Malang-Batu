package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class FragmentDetailListPlaceViewModel(application: Application, placeModel: PlaceModel?) : AndroidViewModel(application) {
    var title: ObservableField<String> = ObservableField(placeModel?.name!!)
    var description: ObservableField<String> = ObservableField(placeModel?.description!!)
    var imageUrl: ObservableField<String> = ObservableField(placeModel?.image!!)
}