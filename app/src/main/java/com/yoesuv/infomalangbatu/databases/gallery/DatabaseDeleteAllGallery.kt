package com.yoesuv.infomalangbatu.databases.gallery

import android.os.AsyncTask

class DatabaseDeleteAllGallery(private val galleryDatabase: GalleryDatabase): AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        galleryDatabase.galleryDaoAccess().deleteAllGallery()
        return null
    }
}