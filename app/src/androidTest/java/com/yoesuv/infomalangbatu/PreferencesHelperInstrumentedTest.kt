package com.yoesuv.infomalangbatu

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoesuv.infomalangbatu.utils.PreferencesHelper
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(AndroidJUnit4::class)
class PreferencesHelperInstrumentedTest {
    private lateinit var preferencesHelper: PreferencesHelper
    private lateinit var context: Context

    // Test keys
    private val testKeyNormal = "test_double_normal"
    private val testKeyZero = "test_double_zero"
    private val testKeyNegative = "test_double_negative"
    private val testKeyLarge = "test_double_large"
    private val testKeySmall = "test_double_small"
    private val testKeyDefault = "test_double_default"

    // Test values
    private val normalValue = 42.5
    private val zeroValue = 0.0
    private val negativeValue = -123.456
    private val largeValue = Double.MAX_VALUE
    private val smallValue = Double.MIN_VALUE

    // Tolerance for floating point comparison
    private val delta = 0.0000001

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        preferencesHelper = PreferencesHelper(context)

        // Clear any existing test values to ensure clean state
        clearTestValues()
    }

    @After
    fun tearDown() {
        // Clean up after tests
        clearTestValues()
    }

    private fun clearTestValues() {
        val sharedPrefs = context.getSharedPreferences("InfoMalangBatuPreferences", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove(testKeyNormal)
        editor.remove(testKeyZero)
        editor.remove(testKeyNegative)
        editor.remove(testKeyLarge)
        editor.remove(testKeySmall)
        editor.remove(testKeyDefault)
        editor.apply()
    }

    @Test
    fun testSetAndGetNormalDouble() {
        // Set a normal double value
        preferencesHelper.setDouble(testKeyNormal, normalValue)

        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(testKeyNormal)

        // Verify the retrieved value matches what was set
        assertEquals(normalValue, retrievedValue, delta)
    }

    @Test
    fun testSetAndGetZeroDouble() {
        // Set zero value
        preferencesHelper.setDouble(testKeyZero, zeroValue)

        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(testKeyZero)

        // Verify the retrieved value is zero
        assertEquals(zeroValue, retrievedValue, delta)
    }

    @Test
    fun testSetAndGetNegativeDouble() {
        // Set negative value
        preferencesHelper.setDouble(testKeyNegative, negativeValue)

        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(testKeyNegative)

        // Verify the retrieved value matches what was set
        assertEquals(negativeValue, retrievedValue, delta)
    }

    @Test
    fun testSetAndGetLargeDouble() {
        // Set maximum double value
        preferencesHelper.setDouble(testKeyLarge, largeValue)

        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(testKeyLarge)

        // Verify the retrieved value matches what was set
        assertEquals(largeValue, retrievedValue, 0.0)
    }

    @Test
    fun testSetAndGetSmallDouble() {
        // Set minimum double value
        preferencesHelper.setDouble(testKeySmall, smallValue)

        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(testKeySmall)

        // Verify the retrieved value matches what was set
        assertEquals(smallValue, retrievedValue, 0.0)
    }

    @Test
    fun testGetDefaultValue() {
        // Try to retrieve a value that hasn't been set
        val retrievedValue = preferencesHelper.getDouble(testKeyDefault)

        // Verify the default value (0.0) is returned
        assertEquals(0.0, retrievedValue, delta)
    }

    @Test
    fun testOverwriteExistingValue() {
        // Set initial value
        preferencesHelper.setDouble(testKeyNormal, normalValue)

        // Verify initial value
        assertEquals(normalValue, preferencesHelper.getDouble(testKeyNormal), delta)

        // Overwrite with new value
        val newValue = 99.9
        preferencesHelper.setDouble(testKeyNormal, newValue)

        // Verify value was updated
        assertEquals(newValue, preferencesHelper.getDouble(testKeyNormal), delta)
    }

    @Test
    fun testPrecisionMaintained() {
        // Test with a value that requires precision
        val preciseValue = 1.0 / 3.0 // 0.3333...

        preferencesHelper.setDouble(testKeyNormal, preciseValue)
        val retrievedValue = preferencesHelper.getDouble(testKeyNormal)

        // Verify precision is maintained
        assertEquals(preciseValue, retrievedValue, delta)
    }
}
