package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.gallery.adapters.GalleryAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.utils.JsonParser
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
        
        // Load test data from JSON file
        galleryItems = loadGalleryItemsFromJson()
        
        // Create adapter with click listener
        adapter = GalleryAdapter { galleryModel ->
            clickedItem.set(galleryModel)
        }
        
        // Submit the list to the adapter
        adapter.submitList(galleryItems)
    }

    @Test
    fun testAdapterItemCount() {
        // Verify that the adapter has the correct number of items
        assertEquals(galleryItems.size, adapter.itemCount)
    }

    /**
     * Helper method to load gallery items from the test JSON file
     */
    private fun loadGalleryItemsFromJson(): List<GalleryModel> {
        return JsonParser.stringToObject("gallery.json", Array<GalleryModel>::class.java).toList()
    }
}
