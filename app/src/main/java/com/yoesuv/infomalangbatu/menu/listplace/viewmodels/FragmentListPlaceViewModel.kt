package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

class FragmentListPlaceViewModel: ViewModel() {

    var isLoading: ObservableField<Boolean> = ObservableField()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getListPlace(){

    }

    fun getListPlaceKabMalang(){

    }

    fun getListPlaceKotaBatu(){

    }

    fun getListPlaceKotaMalang(){

    }

    override fun onCleared() {
        super.onCleared()
    }

}