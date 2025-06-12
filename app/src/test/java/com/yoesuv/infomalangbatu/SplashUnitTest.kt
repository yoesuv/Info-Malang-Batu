package com.yoesuv.infomalangbatu

import android.app.Application
import android.content.Context
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import com.yoesuv.infomalangbatu.main.viewmodels.SplashViewModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.mock.AppRepositoryMock
import com.yoesuv.infomalangbatu.utils.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule = org.junit.rules.TestRule { base, _ ->
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Mockito.mockStatic(Looper::class.java).use { mockedLooper ->
                    mockedLooper.`when`<Looper> { Looper.getMainLooper() }.thenReturn(mock(Looper::class.java))
                    base.evaluate()
                }
            }
        }
    }

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var appDbRepository: AppDbRepository

    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Mock the application context
        `when`(application.applicationContext).thenReturn(context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun loadPlaceModelsFromJson(): Array<PlaceModel> {
        return JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java)
    }

    private fun loadGalleryModelsFromJson(): Array<GalleryModel> {
        return JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java)
    }

    private fun loadMapsPinModelsFromJson(): Array<PinModel> {
        return JsonParser.stringToObject("maps_pin.json", Array<PinModel>::class.java)
    }

    @Test
    fun testInitDatabase() = runTest {
        // Use the AppRepositoryMock
        val mockRepo = AppRepositoryMock()

        // Create a SplashViewModel with the mock repository
        val testViewModel = SplashViewModel(application, mockRepo)

        // Directly inject the mocked AppDbRepository instead of creating a new one
        testViewModel.appDbRepository = appDbRepository

        // Load the test data to verify against
        val places = loadPlaceModelsFromJson().toMutableList()
        val galleries = loadGalleryModelsFromJson().toMutableList()
        val pins = loadMapsPinModelsFromJson().toMutableList()

        // Create an observer for the boolean LiveData
        val observer = mock(Observer::class.java) as Observer<Boolean>
        testViewModel.isDataLoadingComplete.observeForever(observer)

        // Call the actual initDatabase function
        testViewModel.initDataBase {}

        // Wait for coroutines to complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify the database operations were called
        verify(appDbRepository, times(1)).deleteAllPlace()
        verify(appDbRepository, times(1)).deleteAllGallery()
        verify(appDbRepository, times(1)).deleteAllMapPins()
        verify(appDbRepository, times(1)).insertPlaces(places)
        verify(appDbRepository, times(1)).insertGalleries(galleries)
        verify(appDbRepository, times(1)).insertMapPins(pins)

        // Verify the loading state was updated to true (complete)
        val argumentCaptor = ArgumentCaptor.forClass(Boolean::class.java)
        verify(observer, atLeastOnce()).onChanged(argumentCaptor.capture())

        // The last value should be true
        assertTrue(argumentCaptor.value)
    }

    @Test
    fun testSetupProperties() {
        val mockRepo = AppRepositoryMock()
        val splashViewModel = SplashViewModel(application, mockRepo)

        val versionText = "Version 2.3.6"
        `when`(context.getString(R.string.info_app_version, BuildConfig.VERSION_NAME)).thenReturn(versionText)
        splashViewModel.setupProperties(context)
        assertEquals(splashViewModel.version.get(), versionText)
    }
}