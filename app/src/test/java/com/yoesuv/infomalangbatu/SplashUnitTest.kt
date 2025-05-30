package com.yoesuv.infomalangbatu

import android.app.Activity
import android.app.Application
import android.content.Context
import com.yoesuv.infomalangbatu.main.viewmodels.SplashViewModel
import com.yoesuv.infomalangbatu.utils.JsonParser
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.maps.models.PinModel
import com.yoesuv.infomalangbatu.mock.AppRepositoryMock
import com.yoesuv.infomalangbatu.networks.AppRepository
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashUnitTest {

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var appDbRepository: AppDbRepository

    @Mock
    private lateinit var activity: Activity
    
    private var places: List<PlaceModel> = listOf()
    private var galleries: List<GalleryModel> = listOf()
    private var mapsPins: List<PinModel> = listOf()
    private lateinit var splashViewModel: SplashViewModel
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        `when`(application.applicationContext).thenReturn(mock(Context::class.java))
        splashViewModel = SplashViewModel(application, appRepository)
        splashViewModel.appDbRepository = appDbRepository
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Throws(Exception::class)
    private fun loadPlaceModelsFromJson(): List<PlaceModel> {
        val placeModels: Array<PlaceModel> = JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java)
        return placeModels.toList()
    }

    @Throws(Exception::class)
    private fun loadGalleryModelsFromJson(): List<GalleryModel> {
        val galleryModels: Array<GalleryModel> =
            JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java)
        return galleryModels.toList()
    }

    @Throws(Exception::class)
    private fun loadMapsPinModelsFromJson(): List<PinModel> {
        val pinModels: Array<PinModel> = JsonParser.stringToObject("maps_pin.json", Array<PinModel>::class.java)
        return pinModels.toList()
    }

    @Test
    fun placesResponseIsOk() {
        places = loadPlaceModelsFromJson()
        assert(places.isNotEmpty())
        assertEquals(places.size, 3)
    }

    @Test
    fun galleryResponseIsOk() {
        galleries = loadGalleryModelsFromJson()
        assert(galleries.isNotEmpty())
        assertEquals(galleries.size, 3)
    }

    @Test
    fun mapsPinResponseIsOke() {
        mapsPins = loadMapsPinModelsFromJson()
        assert(mapsPins.isNotEmpty())
        assertEquals(mapsPins.size, 2)
    }

    @Test
    fun testSetupProperties() {
        val versionText = "Version 2.3.6"
        `when`(activity.getString(R.string.info_app_version, BuildConfig.VERSION_NAME)).thenReturn(versionText)
        splashViewModel.setupProperties(activity)
        assertEquals(splashViewModel.version.get(), versionText)
    }

    @Test
    fun testInitDatabase() = runTest {
        // Use the AppRepositoryMock
        val mockRepo = AppRepositoryMock()
        
        // Create a SplashViewModel with the mock repository
        val testViewModel = SplashViewModel(application, mockRepo)
        testViewModel.appDbRepository = appDbRepository
        
        // Load the test data to verify against
        val places = loadPlaceModelsFromJson().toMutableList()
        val galleries = loadGalleryModelsFromJson().toMutableList()
        val pins = loadMapsPinModelsFromJson().toMutableList()
        
        // Manually call the setup methods
        testViewModel.setupPlaces(places)
        testViewModel.setupGalleries(galleries)
        testViewModel.setupMapPins(pins)
        
        // Wait for coroutines to complete
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Verify the database operations were called
        verify(appDbRepository, times(1)).deleteAllPlace()
        verify(appDbRepository, times(1)).deleteAllGallery()
        verify(appDbRepository, times(1)).deleteAllMapPins()
        
        verify(appDbRepository, times(1)).insertPlaces(places)
        verify(appDbRepository, times(1)).insertGalleries(galleries)
        verify(appDbRepository, times(1)).insertMapPins(pins)
    }
}