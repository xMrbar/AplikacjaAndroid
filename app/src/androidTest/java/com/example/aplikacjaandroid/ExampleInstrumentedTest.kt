package com.example.aplikacjaandroid

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplikacjaandroid.data.FileManager
import com.example.aplikacjaandroid.model.ItemData
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.aplikacjaandroid", appContext.packageName)
    }

    @Test
    fun testWriteAndRead() {
        val fileName = "test_file.txt"
        val items = listOf(
            "1;32re;6.12.2023;258;INCOME",
            "2;Samochód;3.3.2024;33.33;INCOME",
            "3;Samolot;10.12.2022;1000;INCOME"
        )
        val fileManager = FileManager(fileName)
        fileManager.writeStringItemsToFile(appContext, items)
        val result = fileManager.readItemsFromFile(appContext)
        assertEquals(listOf(
            ItemData(1, 2, "32re", BigDecimal(258), "6.12.2023" ),
            ItemData(2, 2, "Samochód", BigDecimal("33.33"), "3.3.2024"),
            ItemData(3, 2, "Samolot", BigDecimal(1000), "10.12.2022")
        ), result)
    }
}