package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.lifecycle.ViewModel
import android.content.Intent
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import android.view.View
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.views.DetailListPlaceActivity
import java.lang.ref.WeakReference

class ItemListPlaceViewModel(private val weakContext: WeakReference<FragmentActivity>, private val placeModel: PlaceModel): ViewModel() {

    var title: ObservableField<String> = ObservableField(placeModel.name!!)
    var location: ObservableField<String> = ObservableField(placeModel.location!!)
    var imageUrl: ObservableField<String> = ObservableField(placeModel.image!!)

    fun itemClick(view: View){
        val intent = Intent(weakContext.get(), DetailListPlaceActivity::class.java)
        intent.putExtra(DetailListPlaceActivity.EXTRA_DATA_LISTPLACE, placeModel)
        weakContext.get()?.startActivity(intent)
    }

}