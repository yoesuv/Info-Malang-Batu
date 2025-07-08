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
    private val TEST_KEY_NORMAL = "test_double_normal"
    private val TEST_KEY_ZERO = "test_double_zero"
    private val TEST_KEY_NEGATIVE = "test_double_negative"
    private val TEST_KEY_LARGE = "test_double_large"
    private val TEST_KEY_SMALL = "test_double_small"
    private val TEST_KEY_DEFAULT = "test_double_default"
    
    // Test values
    private val NORMAL_VALUE = 42.5
    private val ZERO_VALUE = 0.0
    private val NEGATIVE_VALUE = -123.456
    private val LARGE_VALUE = Double.MAX_VALUE
    private val SMALL_VALUE = Double.MIN_VALUE
    
    // Tolerance for floating point comparison
    private val DELTA = 0.0000001

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
        editor.remove(TEST_KEY_NORMAL)
        editor.remove(TEST_KEY_ZERO)
        editor.remove(TEST_KEY_NEGATIVE)
        editor.remove(TEST_KEY_LARGE)
        editor.remove(TEST_KEY_SMALL)
        editor.remove(TEST_KEY_DEFAULT)
        editor.apply()
    }

    @Test
    fun testSetAndGetNormalDouble() {
        // Set a normal double value
        preferencesHelper.setDouble(TEST_KEY_NORMAL, NORMAL_VALUE)
        
        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_NORMAL)
        
        // Verify the retrieved value matches what was set
        assertEquals(NORMAL_VALUE, retrievedValue, DELTA)
    }

    @Test
    fun testSetAndGetZeroDouble() {
        // Set zero value
        preferencesHelper.setDouble(TEST_KEY_ZERO, ZERO_VALUE)
        
        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_ZERO)
        
        // Verify the retrieved value is zero
        assertEquals(ZERO_VALUE, retrievedValue, DELTA)
    }

    @Test
    fun testSetAndGetNegativeDouble() {
        // Set negative value
        preferencesHelper.setDouble(TEST_KEY_NEGATIVE, NEGATIVE_VALUE)
        
        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_NEGATIVE)
        
        // Verify the retrieved value matches what was set
        assertEquals(NEGATIVE_VALUE, retrievedValue, DELTA)
    }

    @Test
    fun testSetAndGetLargeDouble() {
        // Set maximum double value
        preferencesHelper.setDouble(TEST_KEY_LARGE, LARGE_VALUE)
        
        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_LARGE)
        
        // Verify the retrieved value matches what was set
        assertEquals(LARGE_VALUE, retrievedValue, 0.0)
    }

    @Test
    fun testSetAndGetSmallDouble() {
        // Set minimum double value
        preferencesHelper.setDouble(TEST_KEY_SMALL, SMALL_VALUE)
        
        // Retrieve the value
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_SMALL)
        
        // Verify the retrieved value matches what was set
        assertEquals(SMALL_VALUE, retrievedValue, 0.0)
    }

    @Test
    fun testGetDefaultValue() {
        // Try to retrieve a value that hasn't been set
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_DEFAULT)
        
        // Verify the default value (0.0) is returned
        assertEquals(0.0, retrievedValue, DELTA)
    }
    
    @Test
    fun testOverwriteExistingValue() {
        // Set initial value
        preferencesHelper.setDouble(TEST_KEY_NORMAL, NORMAL_VALUE)
        
        // Verify initial value
        assertEquals(NORMAL_VALUE, preferencesHelper.getDouble(TEST_KEY_NORMAL), DELTA)
        
        // Overwrite with new value
        val newValue = 99.9
        preferencesHelper.setDouble(TEST_KEY_NORMAL, newValue)
        
        // Verify value was updated
        assertEquals(newValue, preferencesHelper.getDouble(TEST_KEY_NORMAL), DELTA)
    }
    
    @Test
    fun testPrecisionMaintained() {
        // Test with a value that requires precision
        val preciseValue = 1.0 / 3.0  // 0.3333...
        
        preferencesHelper.setDouble(TEST_KEY_NORMAL, preciseValue)
        val retrievedValue = preferencesHelper.getDouble(TEST_KEY_NORMAL)
        
        // Verify precision is maintained
        assertEquals(preciseValue, retrievedValue, DELTA)
    }
}
