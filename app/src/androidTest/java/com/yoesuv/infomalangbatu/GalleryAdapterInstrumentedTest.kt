package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.utils.loadGalleryItemsFromJson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class GalleryAdapterInstrumentedTest {

    private lateinit var context: Context
    private lateinit var adapter: GalleryAdapter
    private lateinit var galleryItems: List<GalleryModel>
    private var clickedItem: AtomicReference<GalleryModel?> = AtomicReference(null)
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        recyclerView = RecyclerView(context)

        galleryItems = loadGalleryItemsFromJson()

        adapter = GalleryAdapter { galleryModel ->
            clickedItem.set(galleryModel)
        }

        adapter.submitList(galleryItems)
    }

    @Test
    fun testAdapterItemCount() {
        assertEquals(galleryItems.size, adapter.itemCount)
    }
}
