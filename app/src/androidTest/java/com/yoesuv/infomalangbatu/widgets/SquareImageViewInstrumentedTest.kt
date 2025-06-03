package com.yoesuv.infomalangbatu.widgets

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SquareImageViewInstrumentedTest {

    private lateinit var context: Context
    private lateinit var squareImageView: SquareImageView

    @Before
    fun setUp() {
        // Get the application context
        context = ApplicationProvider.getApplicationContext()
        
        // Create the SquareImageView with a null AttributeSet for testing
        squareImageView = SquareImageView(context)
        
        // Add the view to a parent to ensure proper layout
        val parent = FrameLayout(context)
        parent.addView(squareImageView, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ))
    }

    @Test
    fun testSquareAspectRatio() {
        // Test with a specific width
        val testWidth = 200
        val testHeight = 300 // This should be ignored
        
        // Measure with AT_MOST spec to simulate a parent giving us a maximum size
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            testWidth,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            testHeight,
            View.MeasureSpec.AT_MOST
        )
        
        // Trigger measurement
        squareImageView.measure(widthSpec, heightSpec)
        
        // Verify the view maintains a square aspect ratio (width = height)
        val measuredWidth = squareImageView.measuredWidth
        val measuredHeight = squareImageView.measuredHeight
        
        assertEquals("Width and height should be equal", measuredWidth, measuredHeight)
        assertEquals("Width should match the specified width", testWidth, measuredWidth)
    }

    @Test
    fun testSquareAspectRatioWithExactDimensions() {
        // Test with exact dimensions
        val testWidth = 250
        val testHeight = 400 // Should be ignored
        
        // Set exact dimensions
        squareImageView.layoutParams = ViewGroup.LayoutParams(
            testWidth,
            testHeight
        )
        
        // Measure with EXACTLY spec
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            testWidth,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            testHeight,
            View.MeasureSpec.EXACTLY
        )
        
        // Trigger measurement
        squareImageView.measure(widthSpec, heightSpec)
        
        // Verify the view maintains a square aspect ratio (width = height)
        val measuredWidth = squareImageView.measuredWidth
        val measuredHeight = squareImageView.measuredHeight
        
        assertEquals("Width and height should be equal", measuredWidth, measuredHeight)
        assertEquals("Width should match the specified width", testWidth, measuredWidth)
    }

}
