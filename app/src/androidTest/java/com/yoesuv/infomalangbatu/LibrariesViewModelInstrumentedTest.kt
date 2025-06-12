package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentLibrariesViewModel
import com.yoesuv.infomalangbatu.utils.getOrAwaitValuee
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibrariesViewModelInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ChildFragmentLibrariesViewModel
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = ChildFragmentLibrariesViewModel()
    }

    @Test
    fun testSetupData() {
        // Call the setupData function with the application context
        viewModel.setupData(context)

        // Get the LiveData value using the utility function
        val librariesList = viewModel.listData.getOrAwaitValuee()

        // Verify the list is not null and not empty
        assertNotNull(librariesList)
        assertTrue(librariesList.isNotEmpty())
    }

    @Test
    fun testLibrariesItemsStructure() {
        // Call the setupData function
        viewModel.setupData(context)

        // Get the LiveData value
        val librariesList = viewModel.listData.getOrAwaitValuee()

        // Verify each item has the expected structure
        for (i in librariesList.indices) {
            val item = librariesList[i]
            
            // Check that title, url, and license are not null
            assertNotNull(item.title)
            assertNotNull(item.url)
            assertNotNull(item.license)
            assertNotNull(item.isLast)
            
            // Only the last item should have isLast = true
            if (i == librariesList.size - 1) {
                assertTrue(item.isLast!!)
            } else {
                assertFalse(item.isLast!!)
            }
        }
    }

    @Test
    fun testFirstAndLastItems() {
        // Call the setupData function
        viewModel.setupData(context)

        // Get the LiveData value
        val librariesList = viewModel.listData.getOrAwaitValuee()

        // Verify the first item is not marked as last
        val firstItem = librariesList.first()
        assertFalse(firstItem.isLast!!)

        // Verify the last item is marked as last
        val lastItem = librariesList.last()
        assertTrue(lastItem.isLast!!)
    }
    
    @Test
    fun testExpectedLibrariesCount() {
        // Call the setupData function
        viewModel.setupData(context)
        
        // Get the LiveData value
        val librariesList = viewModel.listData.getOrAwaitValuee()
        
        // The ChildFragmentLibrariesViewModel adds 8 libraries in setupData
        assertEquals(8, librariesList.size)
    }
}
