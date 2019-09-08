package com.yoesuv.infomalangbatu.databases.place

import android.os.AsyncTask

class DatabaseListPlace(private val placeDatabase: PlaceDatabase) :  AsyncTask<Void, Void, MutableList<PlaceRoom>>() {

    override fun doInBackground(vararg p0: Void?): MutableList<PlaceRoom> {
        return placeDatabase.appDaoAccess().selectAll()
    }

}