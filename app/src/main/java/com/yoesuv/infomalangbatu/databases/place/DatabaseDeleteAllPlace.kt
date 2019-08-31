package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask

class DatabaseDeleteAllPlace(private val placeDatabase: PlaceDatabase) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        placeDatabase.appDaoAccess().deleteAllPlace()
        return null
    }
}