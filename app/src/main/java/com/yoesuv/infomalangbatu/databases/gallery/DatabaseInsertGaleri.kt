package com.yoesuv.infomalangbatu.databases.gallery

import android.os.AsyncTask
import com.yoesuv.infomalangbatu.databases.AppDatabase

class DatabaseInsertGaleri(private val appDatabase: AppDatabase, private val galeriRoom: GaleriRoom): AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        appDatabase.appDaoAccess().insertGallery(galeriRoom)
        return null
    }
}