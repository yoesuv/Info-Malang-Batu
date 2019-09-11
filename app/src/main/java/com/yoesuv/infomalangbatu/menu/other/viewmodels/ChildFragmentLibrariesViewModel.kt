package com.yoesuv.infomalangbatu.menu.other.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

class ChildFragmentLibrariesViewModel: ViewModel() {

    private var listLibraries: MutableList<LibraryModel> = mutableListOf()
    var listData: MutableLiveData<MutableList<LibraryModel>> = MutableLiveData()

    fun setupData(context: Context){
        listLibraries.clear()
        listLibraries.add(LibraryModel(context.getString(R.string.aosp), context.getString(R.string.aosp_url), context.getString(R.string.aosp_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.google_direction), context.getString(R.string.google_direction_url), context.getString(R.string.google_direction_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.glide), context.getString(R.string.glide_url), context.getString(R.string.glide_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.icons8), context.getString(R.string.icons8_url), context.getString(R.string.icons8_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.okhttp), context.getString(R.string.okhttp_url), context.getString(R.string.okhttp_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.retrofit), context.getString(R.string.retrofit_url), context.getString(R.string.retrofit_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.sdp_android), context.getString(R.string.sdp_android_url), context.getString(R.string.sdp_android_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.ssp_android), context.getString(R.string.ssp_android_url), context.getString(R.string.sdp_android_license), false))
        listLibraries.add(LibraryModel(context.getString(R.string.toasty), context.getString(R.string.toasty_url), context.getString(R.string.toasty_license), true))
        listData.value = listLibraries
    }

}