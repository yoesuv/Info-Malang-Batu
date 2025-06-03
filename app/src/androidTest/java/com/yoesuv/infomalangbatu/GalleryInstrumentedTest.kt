package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.databases.AppDatabase
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentGalleryViewModel
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import androidx.room.Room
import com.yoesuv.infomalangbatu.utils.getOrAwaitValuee
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class GalleryInstrumentedTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var repository: AppDbRepository
    private lateinit var viewModel: FragmentGalleryViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = AppDbRepository(context)

        // Initialize ViewModel with test repository
        viewModel = FragmentGalleryViewModel()
        viewModel.setupProperties(context)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun loadGallery() {
        runBlocking(Dispatchers.IO) {
            val gallery = GalleryModel(
                caption = "Test Gallery Caption",
                thumbnail = "test_thumb.jpg",
                image = "test_image.jpg"
            )
            gallery.id = 1
            repository.deleteAllGallery()
            repository.insertGalleries(mutableListOf(gallery))
        }
        val gallery = viewModel.getListGallery()?.getOrAwaitValuee()
        assertEquals(gallery?.size, 1)
    }
}
