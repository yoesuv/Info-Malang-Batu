package com.yoesuv.infomalangbatu.networks

import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.data.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Updated by yusuf on 8/27/19.
 */
object ServiceFactory {
    fun create(): RestApi {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        clientBuilder.connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        return retrofit.create(RestApi::class.java)
    }
}