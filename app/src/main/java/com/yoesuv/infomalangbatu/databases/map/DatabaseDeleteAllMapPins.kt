package com.yoesuv.infomalangbatu.databases.map

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseDeleteAllMapPins(private val appDatabase: AppDatabase): AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().deleteAllMapPins()
        return null
    }

}