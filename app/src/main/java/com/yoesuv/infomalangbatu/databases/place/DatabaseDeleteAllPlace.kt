package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask

class DatabaseDeleteAllPlace(private val appDatabase: AppDatabase) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().deleteAllPlace()
        return null
    }
}