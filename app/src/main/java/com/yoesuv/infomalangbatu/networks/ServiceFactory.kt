package com.yoesuv.infomalangbatu.networks

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.security.ProviderInstaller
import com.yoesuv.infomalangbatu.BuildConfig
import com.yoesuv.infomalangbatu.data.AppConstants
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 *  Created by yusuf on 4/12/18.
 */
object ServiceFactory {

    fun create(): RestApi{
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            //for lollipop and latest
            Log.d(AppConstants.TAG_DEBUG,"ServiceFactory # create RestApi for SDK >= 21 (lollipop and latest)")
            val sc = SSLContext.getInstance("SSL")
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                })
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                val allHostsValid = HostnameVerifier { _, _ -> true }
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
            } catch (error: Exception) {
                error.printStackTrace()
            }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(logging)
            clientBuilder.connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            clientBuilder.readTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            clientBuilder.sslSocketFactory(sc.socketFactory, NullX509TrustManager())
            val client = clientBuilder.build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(RestApi::class.java)
        } else {
            //for kitkat and lower
            Log.d(AppConstants.TAG_DEBUG,"ServiceFactory # create RestApi for SDK < 21 (kitkat and lower)")
            val sc = SSLContext.getInstance("TLSv1.2")
            sc.init(null, null, null)

            val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()
            val specs: MutableList<ConnectionSpec> = mutableListOf()
            specs.add(cs)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.sslSocketFactory(Tls12SocketFactory(sc.socketFactory), NullX509TrustManager())
            clientBuilder.connectionSpecs(specs)
            clientBuilder.addInterceptor(logging)
            clientBuilder.connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            clientBuilder.readTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            val client = clientBuilder.build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(RestApi::class.java)
        }
    }

    private open class NullX509TrustManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            println()
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            println()
        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }
}