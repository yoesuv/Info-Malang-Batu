package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.PlaceDaoAccess
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
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

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        placeDao = db.placeDaoAccess()
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
        println("AppDatabaseInstrumentedTest # insertAndReadPlace $placeDao")
        placeDao.insertPlaces(mutableListOf(place))
        val allPlaces =  placeDao.selectAll()
        val places = allPlaces.getOrAwaitValue()
        assertEquals(1, places.size)
        assertEquals("Test Place", places.first().name)
    }
}
