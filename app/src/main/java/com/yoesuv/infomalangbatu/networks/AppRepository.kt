package com.yoesuv.infomalangbatu.networks

import android.content.Context
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppRepository(private val coroutineScope: CoroutineScope) {

    private val restApi = ServiceFactory.create()

    fun getListPlace(onSuccess:(MutableList<PlaceModel>?) -> Unit, onUnSuccess:(Int, String) -> Unit, onError:(Throwable) -> Unit) {
        coroutineScope.launch {
            val result = restApi.getListPlace()
            try {
                if (result.isSuccessful) {
                    onSuccess(result.body())
                } else {
                    onUnSuccess(result.code(), result.message())
                }
            } catch (error: Throwable) {
                onError(error)
            }
        }
    }

}