package com.yoesuv.infomalangbatu.menu.gallery.viewmodels

import android.arch.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.networks.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentGalleryViewModel: ViewModel() {

    private val restApi = ServiceFactory.create()
    private val compositeDisposable = CompositeDisposable()

    fun getGallery(){
        compositeDisposable.add(
                restApi.getGallery()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({},{})
        )
    }

    fun destroy(){
        compositeDisposable.clear()
    }

}