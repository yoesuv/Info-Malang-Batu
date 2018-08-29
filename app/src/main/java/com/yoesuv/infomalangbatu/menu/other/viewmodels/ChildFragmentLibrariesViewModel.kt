package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

class ChildFragmentLibrariesViewModel: ViewModel() {

    private var listLibraries: MutableList<LibraryModel> = mutableListOf()
    var listData: MutableLiveData<MutableList<LibraryModel>> = MutableLiveData()

    fun setupData(context: Context){
        listLibraries.clear()
        listLibraries.add(LibraryModel("custom title", "custom url", "custom license", false))
        listLibraries.add(LibraryModel("custom title 2", "custom url", "custom license", true))
        listData.value = listLibraries
    }

}