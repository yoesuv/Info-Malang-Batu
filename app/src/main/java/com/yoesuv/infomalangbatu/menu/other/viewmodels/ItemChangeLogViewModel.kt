package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class ItemChangeLogViewModel: ViewModel() {

    var version: ObservableField<String> = ObservableField("Versi 1.0.0")
    var description: ObservableField<String> = ObservableField("Versi 1.0.0 Description")
}