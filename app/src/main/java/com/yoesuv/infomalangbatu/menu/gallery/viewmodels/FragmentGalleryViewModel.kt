package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentGalleryViewModel: ViewModel() {

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    var listGalleryResponse: MutableLiveData<MutableList<GalleryModel>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getGallery(){
        compositeDisposable.add(
                restApi.getGallery()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            listGalleryResponse.value = it
                        },{
                            error.value = it
                        })
        )
    }

    fun destroy(){
        compositeDisposable.clear()
    }

}