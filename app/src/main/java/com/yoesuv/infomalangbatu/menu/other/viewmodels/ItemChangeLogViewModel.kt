package com.yoesuv.infomalangbatu.menu.other.viewmodels

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.utils.AppHelper

class ItemChangeLogViewModel(changeLogModel: ChangeLogModel?): ViewModel() {
    var version: ObservableField<String> = ObservableField(changeLogModel?.title!!)
    var description: ObservableField<String> = ObservableField(AppHelper.fromHtml(changeLogModel?.description!!))
    var isLast: ObservableField<Boolean> = ObservableField(changeLogModel?.isLast!!)
}