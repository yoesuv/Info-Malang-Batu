package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

class ItemLibrariesViewModel(libraryModel: LibraryModel): ViewModel() {

    var name: ObservableField<String> = ObservableField(libraryModel.title!!)
    var url: ObservableField<String> = ObservableField(libraryModel.url!!)
    var license: ObservableField<String> = ObservableField(libraryModel.license!!)
    var isLast: ObservableField<Boolean> = ObservableField(libraryModel.isLast!!)

}