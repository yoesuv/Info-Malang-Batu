package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.utils.AppHelper

class ItemChangeLogViewModel(changeLogModel: ChangeLogModel?): ViewModel() {
    var version: ObservableField<String> = ObservableField(changeLogModel?.title!!)
    var description: ObservableField<String> = ObservableField(AppHelper.fromHtml(changeLogModel?.description!!))
}