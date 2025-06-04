package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel
import com.yoesuv.infomalangbatu.utils.getOrAwaitValuee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListPlaceInstrumentedTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var repository: AppDbRepository
    private lateinit var viewModel: FragmentListPlaceViewModel

    // Test data
    private val placeKotaMalang = PlaceModel(
        name = "Test Place Malang",
        location = "Kota Malang",
        category = "Test Category",
        description = "Test Description",
        thumbnail = "test_thumb.jpg",
        image = "test_image.jpg"
    )

    private val placeKotaBatu = PlaceModel(
        name = "Test Place Batu",
        location = "Kota Batu",
        category = "Test Category",
        description = "Test Description",
        thumbnail = "test_thumb.jpg",
        image = "test_image.jpg"
    )

    private val placeKabMalang = PlaceModel(
        name = "Test Place Kab Malang",
        location = "Kab. Malang",
        category = "Test Category",
        description = "Test Description",
        thumbnail = "test_thumb.jpg",
        image = "test_image.jpg"
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = AppDbRepository(context)

        // Initialize ViewModel with test repository
        viewModel = FragmentListPlaceViewModel(repository)

        // Populate test data
        runBlocking(Dispatchers.IO) {
            repository.deleteAllPlace()
            repository.insertPlaces(mutableListOf(placeKotaMalang, placeKotaBatu, placeKabMalang))
        }
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun loadAllPlaces() {
        val places = viewModel.getListPlaceAll()?.getOrAwaitValuee()
        assertEquals(3, places?.size)
    }

    @Test
    fun loadPlacesKotaMalang() {
        val places = viewModel.getListPlace(PlaceLocation.KOTA_MALANG)?.getOrAwaitValuee()
        assertEquals(1, places?.size)
        assertEquals("Test Place Malang", places?.get(0)?.name)
    }

    @Test
    fun loadPlacesKotaBatu() {
        val places = viewModel.getListPlace(PlaceLocation.KOTA_BATU)?.getOrAwaitValuee()
        assertEquals(1, places?.size)
        assertEquals("Test Place Batu", places?.get(0)?.name)
    }

    @Test
    fun loadPlacesKabMalang() {
        val places = viewModel.getListPlace(PlaceLocation.KAB_MALANG)?.getOrAwaitValuee()
        assertEquals(1, places?.size)
        assertEquals("Test Place Kab Malang", places?.get(0)?.name)
    }
}
