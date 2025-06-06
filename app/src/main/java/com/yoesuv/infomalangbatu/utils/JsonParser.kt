package com.yoesuv.infomalangbatu.utils
import com.google.gson.Gson

object JsonParser {

    @JvmStatic
    fun <T> stringToObject(fileName: String, obj: Class<T>): T {
        val iStream = this.javaClass.classLoader?.getResourceAsStream(fileName)
        val size = iStream?.available()
        val buffer = ByteArray(size ?: 0)
        iStream?.read(buffer)
        iStream?.close()
        val json = String(buffer, charset = Charsets.UTF_8)
        return Gson().fromJson(json, obj)
    }

}