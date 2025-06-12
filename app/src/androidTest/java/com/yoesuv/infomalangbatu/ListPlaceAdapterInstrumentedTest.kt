package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.listplace.adapters.ListPlaceAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.utils.loadPlaceItemsFromJson
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

        placeItems = loadPlaceItemsFromJson()

        adapter = ListPlaceAdapter { placeModel ->
            clickedItem.set(placeModel)
        }

        adapter.submitList(placeItems)
    }

    @Test
    fun testAdapterItemCount() {
        assertEquals(placeItems.size, adapter.itemCount)
    }
}
