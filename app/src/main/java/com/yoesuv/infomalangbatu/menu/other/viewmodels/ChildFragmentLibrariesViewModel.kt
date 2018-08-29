package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

class ChildFragmentLibrariesViewModel: ViewModel() {

    private var listLibraries: MutableList<LibraryModel> = mutableListOf()
    var listData: MutableLiveData<MutableList<LibraryModel>> = MutableLiveData()

    fun setupData(context: Context){
        listLibraries.clear()
        listLibraries.add(LibraryModel(context.getString(R.string.google_direction), context.getString(R.string.google_direction_url), context.getString(R.string.google_direction_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.glide), context.getString(R.string.glide_url), context.getString(R.string.glide_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.icons8), context.getString(R.string.icons8_url), context.getString(R.string.icons8_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.navigation_tab_strip), context.getString(R.string.navigation_tab_strip_url), context.getString(R.string.navigation_tab_strip_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.retrofit), context.getString(R.string.retrofit_url), context.getString(R.string.retrofit_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.rxAndroid), context.getString(R.string.rxAndroid_url), context.getString(R.string.rxAndroid_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.rxjava), context.getString(R.string.rxjava_url), context.getString(R.string.rxjava_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.rx_permission), context.getString(R.string.rx_permission_url), context.getString(R.string.rx_permission_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.sdp_android), context.getString(R.string.sdp_android_url), context.getString(R.string.sdp_android_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.ssp_android), context.getString(R.string.ssp_android_url), context.getString(R.string.sdp_android_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.toasty), context.getString(R.string.toasty_url), context.getString(R.string.toasty_license), true))
        listData.value = listLibraries
    }

}