package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.utils.getOrAwaitValue
import com.yoesuv.infomalangbatu.utils.loadGalleryItemsFromJson
import com.yoesuv.infomalangbatu.utils.loadMapsPinItemsFromJson
import com.yoesuv.infomalangbatu.utils.loadPlaceItemsFromJson
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDbRepositoryInstrumentedTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: AppDbRepository
    private lateinit var placeItems: List<PlaceModel>
    private lateinit var galleryItems: List<GalleryModel>
    private lateinit var pinItems: List<PinModel>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = AppDbRepository(context)

        placeItems = loadPlaceItemsFromJson()
        galleryItems = loadGalleryItemsFromJson()
        pinItems = loadMapsPinItemsFromJson()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndReadPlace() = runBlocking {
        val place = placeItems.firstOrNull()?.copy() ?: PlaceModel(
            name = "Test Place",
            location = "Test Location",
            category = "Test Category",
            description = "Test Description",
            thumbnail = "test_thumb.jpg",
            image = "test.jpg"
        )
        place.id = 1
        repository.deleteAllPlace()
        repository.insertPlaces(mutableListOf(place))
        val allPlaces = repository.selectAllPlace()
        val places = allPlaces?.getOrAwaitValue() ?: emptyList()
        assertEquals(1, places.size)
        assertEquals(place.name, places.first().name)
    }

    @Test
    fun insertAndReadGallery() = runBlocking {
        val gallery = galleryItems.firstOrNull()?.copy() ?: GalleryModel(
            caption = "Repo Test Caption",
            thumbnail = "repo_thumb.jpg",
            image = "repo_image.jpg"
        )
        gallery.id = 1
        repository.deleteAllGallery()
        repository.insertGalleries(mutableListOf(gallery))
        val allGalleries = repository.selectAllGallery()
        val galleries = allGalleries?.getOrAwaitValue() ?: emptyList()
        assertEquals(1, galleries.size)
        assertEquals(gallery.caption, galleries.first().caption)
    }

    @Test
    fun insertAndReadPin() = runBlocking {
        val pin = pinItems.firstOrNull()?.copy() ?: PinModel(
            name = "Test Pin",
            location = 123,
            latitude = -7.982,
            longitude = 112.630
        )
        pin.id = 1
        repository.deleteAllMapPins()
        repository.insertMapPins(mutableListOf(pin))
        val allPins = repository.selectAllMapPins()
        val pins = allPins?.getOrAwaitValue() ?: emptyList()
        assertEquals(1, pins.size)
        assertEquals(pin.name, pins.first().name)
    }
}
