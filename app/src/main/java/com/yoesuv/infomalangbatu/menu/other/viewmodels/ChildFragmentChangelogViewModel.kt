package com.yoesuv.infomalangbatu.menu.other.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel

class ChildFragmentChangelogViewModel : ViewModel() {
    private var listChangelog: MutableList<ChangeLogModel> = mutableListOf()
    var listData: MutableLiveData<MutableList<ChangeLogModel>> = MutableLiveData()

    fun setupData(context: Context) {
        listChangelog.clear()
        listChangelog =
            changelogEntries
                .mapIndexed { index, resIds ->
                    ChangeLogModel(
                        context.getString(resIds[0]),
                        context.getString(resIds[1]),
                        index == changelogEntries.lastIndex,
                    )
                }.toMutableList()
        listData.value = listChangelog
    }

    companion object {
        // Each entry: [titleRes, descriptionRes]
        private val changelogEntries =
            listOf(
                intArrayOf(R.string.ver_18, R.string.ver_18_info),
                intArrayOf(R.string.ver_17, R.string.ver_17_info),
                intArrayOf(R.string.ver_16, R.string.ver_16_info),
                intArrayOf(R.string.ver_15, R.string.ver_15_info),
                intArrayOf(R.string.ver_14, R.string.ver_14_info),
                intArrayOf(R.string.ver_13, R.string.ver_13_info),
                intArrayOf(R.string.ver_12, R.string.ver_12_info),
                intArrayOf(R.string.ver_11, R.string.ver_11_info),
                intArrayOf(R.string.ver_10, R.string.ver_10_info),
                intArrayOf(R.string.ver_9, R.string.ver_9_info),
                intArrayOf(R.string.ver_8, R.string.ver_8_info),
                intArrayOf(R.string.ver_7, R.string.ver_7_info),
                intArrayOf(R.string.ver_6, R.string.ver_6_info),
                intArrayOf(R.string.ver_5, R.string.ver_5_info),
                intArrayOf(R.string.ver_4, R.string.ver_4_info),
                intArrayOf(R.string.ver_3, R.string.ver_3_info),
                intArrayOf(R.string.ver_2, R.string.ver_2_info),
                intArrayOf(R.string.ver_1, R.string.ver_1_info),
            )
    }
}
