package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ChildFragmentChangelogViewModel
import com.yoesuv.infomalangbatu.utils.getOrAwaitValuee
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangelogViewModelInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ChildFragmentChangelogViewModel
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        viewModel = ChildFragmentChangelogViewModel()
    }

    @Test
    fun testSetupData() {
        // Call the setupData function with the application context
        viewModel.setupData(context)

        // Get the LiveData value using the utility function
        val changelogList = viewModel.listData.getOrAwaitValuee()

        // Verify the list is not null and not empty
        assertNotNull(changelogList)
        assertTrue(changelogList.isNotEmpty())
    }

    @Test
    fun testChangelogItemsStructure() {
        // Call the setupData function
        viewModel.setupData(context)

        // Get the LiveData value
        val changelogList = viewModel.listData.getOrAwaitValuee()

        // Verify each item has the expected structure
        for (i in changelogList.indices) {
            val item = changelogList[i]

            // Check that title and description are not null
            assertNotNull(item.title)
            assertNotNull(item.description)
            assertNotNull(item.isLast)

            // Only the last item should have isLast = true
            if (i == changelogList.size - 1) {
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
        val changelogList = viewModel.listData.getOrAwaitValuee()

        // Verify the first item (newest version) is not marked as last
        val firstItem = changelogList.first()
        assertFalse(firstItem.isLast!!)

        // Verify the last item (oldest version) is marked as last
        val lastItem = changelogList.last()
        assertTrue(lastItem.isLast!!)
    }
}
