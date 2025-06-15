package com.yoesuv.infomalangbatu

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yoesuv.infomalangbatu.utils.AppHelper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After

@RunWith(AndroidJUnit4::class)
class AppHelperInstrumentedTest {

    private lateinit var context: Context
    private lateinit var testView: View
    
    // Test values
    private val TEST_PADDING_LEFT = 10
    private val TEST_PADDING_TOP = 20
    private val TEST_PADDING_RIGHT = 30
    private val TEST_PADDING_BOTTOM = 40
    private val TEST_COLOR = Color.RED

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        
        // Create a test view with initial padding
        testView = FrameLayout(context)
        testView.setPadding(
            TEST_PADDING_LEFT,
            TEST_PADDING_TOP,
            TEST_PADDING_RIGHT,
            TEST_PADDING_BOTTOM
        )
    }

    @After
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun testInsetsPaddingNoInsets() {
        // Apply insets padding with all parameters set to false
        AppHelper.insetsPadding(
            testView,
            left = false,
            top = false,
            right = false,
            bottom = false
        )
        
        // Trigger the window insets listener manually
        val windowInsetsCompat = createMockWindowInsets(50, 60, 70, 80)
        ViewCompat.dispatchApplyWindowInsets(testView, windowInsetsCompat)
        
        // Verify that padding remains unchanged
        assertEquals(TEST_PADDING_LEFT, testView.paddingLeft)
        assertEquals(TEST_PADDING_TOP, testView.paddingTop)
        assertEquals(TEST_PADDING_RIGHT, testView.paddingRight)
        assertEquals(TEST_PADDING_BOTTOM, testView.paddingBottom)
    }

    @Test
    fun testInsetsPaddingAllInsets() {
        // Apply insets padding with all parameters set to true
        AppHelper.insetsPadding(
            testView,
            left = true,
            top = true,
            right = true,
            bottom = true
        )
        
        // Mock inset values
        val insetLeft = 50
        val insetTop = 60
        val insetRight = 70
        val insetBottom = 80
        
        // Trigger the window insets listener manually
        val windowInsetsCompat = createMockWindowInsets(insetLeft, insetTop, insetRight, insetBottom)
        ViewCompat.dispatchApplyWindowInsets(testView, windowInsetsCompat)
        
        // Verify that padding is updated with inset values
        // Note: bottom padding has an additional 32dp added according to the implementation
        assertEquals(insetLeft, testView.paddingLeft)
        assertEquals(insetTop, testView.paddingTop)
        assertEquals(insetRight, testView.paddingRight)
        assertEquals(insetBottom + 32, testView.paddingBottom)
    }

    @Test
    fun testInsetsPaddingPartialInsets() {
        // Apply insets padding with only left and right set to true
        AppHelper.insetsPadding(
            testView,
            left = true,
            top = false,
            right = true,
            bottom = false
        )
        
        // Mock inset values
        val insetLeft = 50
        val insetTop = 60
        val insetRight = 70
        val insetBottom = 80
        
        // Trigger the window insets listener manually
        val windowInsetsCompat = createMockWindowInsets(insetLeft, insetTop, insetRight, insetBottom)
        ViewCompat.dispatchApplyWindowInsets(testView, windowInsetsCompat)
        
        // Verify that only left and right padding are updated
        assertEquals(insetLeft, testView.paddingLeft)
        assertEquals(TEST_PADDING_TOP, testView.paddingTop)
        assertEquals(insetRight, testView.paddingRight)
        assertEquals(TEST_PADDING_BOTTOM, testView.paddingBottom)
    }

    @Test
    fun testInsetsPaddingBottomOnly() {
        // Apply insets padding with only bottom set to true
        AppHelper.insetsPadding(
            testView,
            left = false,
            top = false,
            right = false,
            bottom = true
        )
        
        // Mock inset values
        val insetLeft = 50
        val insetTop = 60
        val insetRight = 70
        val insetBottom = 80
        
        // Trigger the window insets listener manually
        val windowInsetsCompat = createMockWindowInsets(insetLeft, insetTop, insetRight, insetBottom)
        ViewCompat.dispatchApplyWindowInsets(testView, windowInsetsCompat)
        
        // Verify that only bottom padding is updated with the additional 32dp
        assertEquals(TEST_PADDING_LEFT, testView.paddingLeft)
        assertEquals(TEST_PADDING_TOP, testView.paddingTop)
        assertEquals(TEST_PADDING_RIGHT, testView.paddingRight)
        assertEquals(insetBottom + 32, testView.paddingBottom)
    }

    /**
     * Creates a mock WindowInsetsCompat with the specified inset values
     */
    private fun createMockWindowInsets(left: Int, top: Int, right: Int, bottom: Int): WindowInsetsCompat {
        // Create a WindowInsetsCompat with system bar insets
        return WindowInsetsCompat.Builder()
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                androidx.core.graphics.Insets.of(left, top, right, bottom)
            )
            .build()
    }
}
