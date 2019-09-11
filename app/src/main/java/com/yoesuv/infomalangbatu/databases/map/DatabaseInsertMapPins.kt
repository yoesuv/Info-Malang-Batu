package com.yoesuv.infomalangbatu.databases.map

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseInsertMapPins(private val appDatabase: AppDatabase, private val mapPinsRoom: MapPinsRoom): AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().insertMapPins(mapPinsRoom)
        return null
    }

}