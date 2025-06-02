package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.GalleryDaoAccess
import com.yoesuv.infomalangbatu.databases.MapPinDaoAccess
import com.yoesuv.infomalangbatu.databases.PlaceDaoAccess
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseInstrumentedTest {
    private lateinit var db: AppDatabase
    private lateinit var placeDao: PlaceDaoAccess
    private lateinit var galleryDao: GalleryDaoAccess
    private lateinit var mapPinDao: MapPinDaoAccess

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        placeDao = db.placeDaoAccess()
        galleryDao = db.galleryDaoAccess()
        mapPinDao = db.mapPinDaoAccess()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndReadPlace() = runBlocking {
        val place = PlaceModel(
            name = "Test Place",
            location = "Test Location",
            category = "Test Category",
            description = "Test Description",
            thumbnail = "test_thumb.jpg",
            image = "test.jpg"
        )
        placeDao.deleteAllPlace()
        placeDao.insertPlaces(mutableListOf(place))
        val allPlaces = placeDao.selectAll()
        val places = allPlaces.getOrAwaitValue()
        assertEquals(1, places.size)
        assertEquals("Test Place", places.first().name)
    }

    @Test
    fun insertAndReadGallery() = runBlocking {
        val gallery = GalleryModel(
            caption = "Test Caption",
            thumbnail = "test_thumb.jpg",
            image = "test.jpg"
        )
        galleryDao.deleteAllDbGallery()
        galleryDao.insertDbGalleries(listOf(gallery))
        val allGalleries = galleryDao.selectAllDbGallery()
        val galleries = allGalleries.getOrAwaitValue()
        assertEquals(1, galleries.size)
        assertEquals("Test Caption", galleries.first().caption)
    }

    @Test
    fun insertAndReadPin() = runBlocking {
        val pin = PinModel(
            name = "Test Pin",
            location = 123,
            latitude = -7.982,
            longitude = 112.630
        )
        mapPinDao.deleteAllDbMapPins()
        mapPinDao.insertDbMapPins(listOf(pin))
        val allPins = mapPinDao.selectAllDbMapPins()
        val pins = allPins.getOrAwaitValue()
        assertEquals(1, pins.size)
        assertEquals("Test Pin", pins.first().name)
        assertEquals(123, pins.first().location)
    }
}
