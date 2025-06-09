package com.yoesuv.infomalangbatu

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.FragmentDetailGalleryViewModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel
import com.yoesuv.infomalangbatu.utils.loadGalleryItemsFromJson
import com.yoesuv.infomalangbatu.utils.loadPlaceItemsFromJson
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
        val placeModel = placeItems[0]
        val viewModel = FragmentDetailListPlaceViewModel(placeModel)

        assertEquals(placeModel.name, viewModel.title.get())
        assertEquals(placeModel.image, viewModel.imageUrl.get())
        assertEquals(placeModel.description, viewModel.description.get())
    }

    @Test
    fun testFragmentDetailGalleryViewModel() {
        val galleryModel = galleryItems[0]
        val viewModel = FragmentDetailGalleryViewModel(galleryModel)

        assertEquals(galleryModel.image, viewModel.imageUrl.get())
        assertEquals(galleryModel.caption, viewModel.caption.get())
    }

    @Test
    fun testFragmentDetailListPlaceViewModel_NullSafety() {
        val placeModel = PlaceModel(
            name = null,
            location = null,
            category = null,
            description = null,
            thumbnail = null,
            image = null
        )

        var exceptionThrown = false
        try {
            FragmentDetailListPlaceViewModel(placeModel)
        } catch (e: Exception) {
            exceptionThrown = true
        }

        assertEquals(true, exceptionThrown)
    }

    @Test
    fun testFragmentDetailGalleryViewModel_NullValues() {
        val galleryModel = GalleryModel(
            caption = null,
            thumbnail = null,
            image = null
        )

        val viewModel = FragmentDetailGalleryViewModel(galleryModel)

        assertEquals(null, viewModel.imageUrl.get())
        assertEquals(null, viewModel.caption.get())
    }
}
