package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseInsertPlace(private val appDatabase: AppDatabase, private val placeRoom: PlaceRoom): AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().insertPlace(placeRoom)
        return null
    }
}