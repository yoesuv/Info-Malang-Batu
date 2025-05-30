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
import com.yoesuv.infomalangbatu.networks.AppRepository
import com.yoesuv.infomalangbatu.databases.AppDbRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.mockito.Mock

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

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(application.applicationContext).thenReturn(mock(Context::class.java))
        splashViewModel = SplashViewModel(application, appRepository)
        // Set the appDbRepository field in SplashViewModel using reflection
        val field = SplashViewModel::class.java.getDeclaredField("appDbRepository")
        field.isAccessible = true
        field.set(splashViewModel, appDbRepository)
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
    fun testSetupPlaces() = runBlocking {
        places = loadPlaceModelsFromJson()
        val mutablePlaces = places.toMutableList()

        splashViewModel.setupPlaces(mutablePlaces)

        // Verify that deleteAllPlace was called
        verify(appDbRepository, times(1)).deleteAllPlace()

        // Verify that insertPlaces was called with the correct data
        verify(appDbRepository, times(1)).insertPlaces(mutablePlaces)

        // Test with null places
        splashViewModel.setupPlaces(null)

        // Verify deleteAllPlace was called again
        verify(appDbRepository, times(2)).deleteAllPlace()

        // But insertPlaces should not be called again with null data
        verify(appDbRepository, times(1)).insertPlaces(mutablePlaces)
    }

    @Test
    fun testSetupGallery() = runBlocking {
        galleries = loadGalleryModelsFromJson()
        val mutableGalleries = galleries.toMutableList()

        splashViewModel.setupGalleries(mutableGalleries)

        // Verify that deleteAllGallery was called
        verify(appDbRepository, times(1)).deleteAllGallery()

        // Verify that insertGalleries was called with the correct data
        verify(appDbRepository, times(1)).insertGalleries(mutableGalleries)

        // Test with null galleries
        splashViewModel.setupGalleries(null)

        // Verify deleteAllGallery was called again
        verify(appDbRepository, times(2)).deleteAllGallery()

        // But insertGalleries should not be called again with null data
        verify(appDbRepository, times(1)).insertGalleries(mutableGalleries)
    }

    @Test
    fun testSetupMapPins() = runBlocking {
        mapsPins = loadMapsPinModelsFromJson()
        val mutableMapPins = mapsPins.toMutableList()

        splashViewModel.setupMapPins(mutableMapPins)

        // Verify that deleteAllMapPins was called
        verify(appDbRepository, times(1)).deleteAllMapPins()

        // Verify that insertMapPins was called with the correct data
        verify(appDbRepository, times(1)).insertMapPins(mutableMapPins)

        // Test with null map pins
        splashViewModel.setupMapPins(null)

        // Verify deleteAllMapPins was called again
        verify(appDbRepository, times(2)).deleteAllMapPins()

        // But insertMapPins should not be called again with null data
        verify(appDbRepository, times(1)).insertMapPins(mutableMapPins)
    }
}