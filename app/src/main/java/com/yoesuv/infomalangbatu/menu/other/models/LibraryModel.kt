package com.yoesuv.infomalangbatu.menu.other.models

import androidx.annotation.Keep

@Keep
data class LibraryModel(
        val title: String?,
        val url: String?,
        val license: String?,
        val isLast: Boolean?
)