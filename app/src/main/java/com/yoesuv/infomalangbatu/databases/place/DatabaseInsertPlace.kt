package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask

class DatabaseInsertPlace(private val placeDatabase: PlaceDatabase, private val placeRoom: PlaceRoom): AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        placeDatabase.appDaoAccess().insertPlace(placeRoom)
        return null
    }
}