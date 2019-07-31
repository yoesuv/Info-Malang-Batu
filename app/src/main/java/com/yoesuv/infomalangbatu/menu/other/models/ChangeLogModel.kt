package com.yoesuv.infomalangbatu.menu.other.models

import androidx.annotation.Keep

@Keep
data class ChangeLogModel(
        val title: String?,
        val description: String?,
        val isLast: Boolean?
)