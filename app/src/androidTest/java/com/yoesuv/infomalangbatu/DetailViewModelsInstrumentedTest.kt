package com.yoesuv.infomalangbatu

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailViewModelsInstrumentedTest {

    private val testPlace = PlaceModel(
        name = "Test Place",
        location = "Test Location",
        category = "Test Category",
        description = "Test Description",
        thumbnail = "test_thumb.jpg",
        image = "test_image.jpg"
    )

    private val testGallery = GalleryModel(
        caption = "Test Caption",
        thumbnail = "test_gallery_thumb.jpg",
        image = "test_gallery_image.jpg"
    )

    @Test
    fun testFragmentDetailListPlaceViewModel() {
        val viewModel = FragmentDetailListPlaceViewModel(testPlace)

        assertEquals("Test Place", viewModel.title.get())
        assertEquals("Test Description", viewModel.description.get())
        assertEquals("test_image.jpg", viewModel.imageUrl.get())
    }

    @Test
    fun testFragmentDetailGalleryViewModel() {
        val viewModel = FragmentDetailGalleryViewModel(testGallery)

        assertEquals("test_gallery_image.jpg", viewModel.imageUrl.get())
        assertEquals("Test Caption", viewModel.caption.get())
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
}
