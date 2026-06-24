package com.yoesuv.infomalangbatu.data

enum class PlaceLocation {
    ALL,
    KOTA_MALANG {
        override fun toString(): String = "Kota Malang"
    },
    KOTA_BATU {
        override fun toString(): String = "Kota Batu"
    },
    KAB_MALANG {
        override fun toString(): String = "Kab. Malang"
    },
}
