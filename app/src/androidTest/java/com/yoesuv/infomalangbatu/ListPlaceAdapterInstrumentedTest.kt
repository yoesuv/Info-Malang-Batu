package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.listplace.adapters.ListPlaceAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.utils.JsonParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class ListPlaceAdapterInstrumentedTest {

    private lateinit var context: Context
    private lateinit var adapter: ListPlaceAdapter
    private lateinit var placeItems: List<PlaceModel>
    private var clickedItem: AtomicReference<PlaceModel?> = AtomicReference(null)
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        recyclerView = RecyclerView(context)
        
        // Load test data from JSON file
        placeItems = loadPlaceItemsFromJson()
        
        // Create adapter with click listener
        adapter = ListPlaceAdapter { placeModel ->
            clickedItem.set(placeModel)
        }
        
        // Submit the list to the adapter
        adapter.submitList(placeItems)
    }

    @Test
    fun testAdapterItemCount() {
        // Verify that the adapter has the correct number of items
        assertEquals(placeItems.size, adapter.itemCount)
    }

    /**
     * Helper method to load place items from the test JSON file
     */
    private fun loadPlaceItemsFromJson(): List<PlaceModel> {
        return JsonParser.stringToObject("list_place.json", Array<PlaceModel>::class.java).toList()
    }
}
