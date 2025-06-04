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

    // Test data for PlaceModel
    private val testPlace = PlaceModel(
        name = "Test Place",
        location = "Test Location",
        category = "Test Category",
        description = "Test Description",
        thumbnail = "test_thumb.jpg",
        image = "test_image.jpg"
    )

    // Test data for GalleryModel
    private val testGallery = GalleryModel(
        caption = "Test Caption",
        thumbnail = "test_gallery_thumb.jpg",
        image = "test_gallery_image.jpg"
    )

    @Test
    fun testFragmentDetailListPlaceViewModel() {
        // Initialize the ViewModel with test data
        val viewModel = FragmentDetailListPlaceViewModel(testPlace)

        // Verify that the ObservableFields are correctly initialized
        assertEquals("Test Place", viewModel.title.get())
        assertEquals("Test Description", viewModel.description.get())
        assertEquals("test_image.jpg", viewModel.imageUrl.get())
    }

    @Test
    fun testFragmentDetailGalleryViewModel() {
        // Initialize the ViewModel with test data
        val viewModel = FragmentDetailGalleryViewModel(testGallery)

        // Verify that the ObservableFields are correctly initialized
        assertEquals("test_gallery_image.jpg", viewModel.imageUrl.get())
        assertEquals("Test Caption", viewModel.caption.get())
    }

    @Test
    fun testFragmentDetailListPlaceViewModel_NullSafety() {
        // Create a PlaceModel with null values to test null safety
        val nullPlace = PlaceModel(
            name = null,
            location = null,
            category = null,
            description = null,
            thumbnail = null,
            image = null
        )

        // This should throw an exception due to null assertion in the ViewModel
        // We're expecting an exception, so we'll catch it to pass the test
        var exceptionThrown = false
        try {
            FragmentDetailListPlaceViewModel(nullPlace)
        } catch (e: Exception) {
            exceptionThrown = true
        }
        
        // The test passes if an exception was thrown
        assertEquals(true, exceptionThrown)
    }

    @Test
    fun testFragmentDetailGalleryViewModel_NullValues() {
        // Create a GalleryModel with null values
        val nullGallery = GalleryModel(
            caption = null,
            thumbnail = null,
            image = null
        )

        // Initialize the ViewModel with null data
        val viewModel = FragmentDetailGalleryViewModel(nullGallery)

        // Verify that the ObservableFields handle null values correctly
        assertEquals(null, viewModel.imageUrl.get())
        assertEquals(null, viewModel.caption.get())
    }
}
