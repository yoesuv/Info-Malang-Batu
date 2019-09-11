package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseListPlace(private val appDatabase: AppDatabase) :  AsyncTask<Void, Void, MutableList<PlaceRoom>>() {

    override fun doInBackground(vararg p0: Void?): MutableList<PlaceRoom> {
        return appDatabase.appDaoAccess().selectAll()
    }

}