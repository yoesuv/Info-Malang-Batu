package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentListPlaceViewModel: ViewModel() {

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getListPlace(){
        compositeDisposable.add(
                restApi.getListPlace()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            listPlaceResponse.value = it
                        },{
                            error.value = it
                        })
        )
    }

    fun destroy(){
        compositeDisposable.clear()
    }

}