package com.yoesuv.infomalangbatu.databases.map

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseListMapPins(private val appDatabase: AppDatabase): AsyncTask<Void, Void, MutableList<MapPinsRoom>>() {

    override fun doInBackground(vararg p0: Void?): MutableList<MapPinsRoom> {
        return appDatabase.appDaoAccess().selectAllMapPins()
    }

}