package com.yoesuv.infomalangbatu

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel
import com.yoesuv.infomalangbatu.utils.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailViewModelsInstrumentedTest {

    private lateinit var placeItems: List<PlaceModel>
    private lateinit var galleryItems: List<GalleryModel>

    @Before
    fun setUp() {
        placeItems = loadPlaceItemsFromJson()
        galleryItems = loadGalleryItemsFromJson()
    }

    @Test
    fun testFragmentDetailListPlaceViewModel() {
        val testPlace = placeItems[0]
        val viewModel = FragmentDetailListPlaceViewModel(testPlace)

        assertEquals("Alun Alun Malang", viewModel.title.get())
        assertEquals("alun alun ini baru saja dipermak pada tahun 2015. yang sebelumnya terlihat semrawut sekarang menjadi lebih tertata.", viewModel.description.get())
        assertEquals("https://lh3.googleusercontent.com/-rTHiiW3vPMk/VqQXrbG5u6I/AAAAAAAACfs/buFhkMyTN98/s600-Ic42/alun_alun_malang.jpg", viewModel.imageUrl.get())
    }

    @Test
    fun testFragmentDetailGalleryViewModel() {
        val testGallery = galleryItems[0]
        val viewModel = FragmentDetailGalleryViewModel(testGallery)

        assertEquals("https://images2.imgbox.com/0a/e7/G421oy0Q_o.jpg", viewModel.imageUrl.get())
        assertEquals("Zona Infinite World di Jatim Park 3", viewModel.caption.get())
    }

    @Test
    fun testFragmentDetailListPlaceViewModel_NullSafety() {
        val nullPlace = PlaceModel(
            name = null,
            location = null,
            category = null,
            description = null,
            thumbnail = null,
            image = null
        )

        var exceptionThrown = false
        try {
            FragmentDetailListPlaceViewModel(nullPlace)
        } catch (e: Exception) {
            exceptionThrown = true
        }

        assertEquals(true, exceptionThrown)
    }

    @Test
    fun testFragmentDetailGalleryViewModel_NullValues() {
        val nullGallery = GalleryModel(
            caption = null,
            thumbnail = null,
            image = null
        )

        val viewModel = FragmentDetailGalleryViewModel(nullGallery)

        assertEquals(null, viewModel.imageUrl.get())
        assertEquals(null, viewModel.caption.get())
    }

    private fun loadPlaceItemsFromJson(): List<PlaceModel> {
        return JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java).toList()
    }

    private fun loadGalleryItemsFromJson(): List<GalleryModel> {
        return JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java).toList()
    }
}
