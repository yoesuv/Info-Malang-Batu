package com.yoesuv.infomalangbatu.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            runOnUiThread {
                removeObserver(this)
            }
        }
    }
    runOnUiThread {
        observeForever(observer)
    }

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    } finally {
        runOnUiThread {
            removeObserver(observer)
        }
    }
}

fun <T> LiveData<T>.getOrAwaitValuee(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValuee.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun loadPlaceItemsFromJson(): List<PlaceModel> {
    return JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java).toList()
}

fun loadGalleryItemsFromJson(): List<GalleryModel> {
    return JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java).toList()
}
