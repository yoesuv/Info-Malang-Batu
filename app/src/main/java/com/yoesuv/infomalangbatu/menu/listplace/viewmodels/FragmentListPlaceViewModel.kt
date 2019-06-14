package com.yoesuv.infomalangbatu.menu.listplace.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentListPlaceViewModel: ViewModel() {

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    var isLoading: ObservableField<Boolean> = ObservableField()

    var listPlaceResponse: MutableLiveData<MutableList<PlaceModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getListPlace(){
        isLoading.set(true)
        compositeDisposable.add(
                restApi.getListPlace()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isLoading.set(false)
                            listPlaceResponse.value = it
                        },{
                            isLoading.set(false)
                            error.value = it
                        })
        )
    }

    fun getListPlaceKabMalang(){
        isLoading.set(true)
        compositeDisposable.add(
                restApi.getListPlaceKabMalang()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isLoading.set(false)
                            listPlaceResponse.value = it
                        },{
                            isLoading.set(false)
                            error.value = it
                        })
        )
    }

    fun getListPlaceKotaBatu(){
        isLoading.set(true)
        compositeDisposable.add(
                restApi.getListPlaceKotaBatu()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isLoading.set(false)
                            listPlaceResponse.value = it
                        },{
                            isLoading.set(false)
                            error.value = it
                        })
        )
    }

    fun getListPlaceKotaMalang(){
        isLoading.set(true)
        compositeDisposable.add(
                restApi.getListPlaceKotaMalang()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            isLoading.set(false)
                            listPlaceResponse.value = it
                        },{
                            isLoading.set(false)
                            error.value = it
                        })
        )
    }

    fun destroy(){
        compositeDisposable.clear()
    }

}