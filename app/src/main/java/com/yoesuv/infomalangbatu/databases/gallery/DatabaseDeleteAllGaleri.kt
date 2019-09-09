package com.yoesuv.infomalangbatu.databases.gallery

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseDeleteAllGaleri(private val appDatabase: AppDatabase): AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().deleteAllGallery()
        return null
    }
}