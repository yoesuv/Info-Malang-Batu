package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

class ChildFragmentLibrariesViewModel : ViewModel() {
    private var listLibraries: MutableList<LibraryModel> = mutableListOf()
    var listData: MutableLiveData<MutableList<LibraryModel>> = MutableLiveData()

    fun setupData(context: Context) {
        listLibraries.clear()
        listLibraries =
            libraryEntries
                .mapIndexed { index, resIds ->
                    LibraryModel(
                        context.getString(resIds[0]),
                        context.getString(resIds[1]),
                        context.getString(resIds[2]),
                        index == libraryEntries.lastIndex,
                    )
                }.toMutableList()
        listData.value = listLibraries
    }

    companion object {
        // Each entry: [nameRes, urlRes, licenseRes]
        private val libraryEntries =
            listOf(
                intArrayOf(R.string.aosp, R.string.aosp_url, R.string.aosp_license),
                intArrayOf(R.string.google_direction, R.string.google_direction_url, R.string.google_direction_license),
                intArrayOf(R.string.glide, R.string.glide_url, R.string.glide_license),
                intArrayOf(R.string.icons8, R.string.icons8_url, R.string.icons8_license),
                intArrayOf(R.string.okhttp, R.string.okhttp_url, R.string.okhttp_license),
                intArrayOf(R.string.retrofit, R.string.retrofit_url, R.string.retrofit_license),
                intArrayOf(R.string.sdp_android, R.string.sdp_android_url, R.string.sdp_android_license),
                intArrayOf(R.string.ssp_android, R.string.ssp_android_url, R.string.sdp_android_license),
            )
    }
}
