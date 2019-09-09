package com.yoesuv.infomalangbatu.databases.gallery

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseListGaleri(private val appDatabase: AppDatabase): AsyncTask<Void, Void, MutableList<GaleriRoom>>() {

    override fun doInBackground(vararg p0: Void?): MutableList<GaleriRoom> {
        return appDatabase.appDaoAccess().selectAllGallery()
    }

}