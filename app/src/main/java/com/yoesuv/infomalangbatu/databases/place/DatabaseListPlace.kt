package com.yoesuv.infomalangbatu.databases.place

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databases.PlaceDatabase
import com.yoesuv.infomalangbatu.databases.PlaceRoom

class DatabaseListPlace(context: Context) :  AsyncTask<Void, Void, MutableList<PlaceRoom>>() {

    private var placeDatabase = Room.databaseBuilder(context, PlaceDatabase::class.java, AppConstants.DATABASE_NAME).build()

    override fun doInBackground(vararg p0: Void?): MutableList<PlaceRoom> {
        return placeDatabase.appDaoAccess().selectAll()
    }

}