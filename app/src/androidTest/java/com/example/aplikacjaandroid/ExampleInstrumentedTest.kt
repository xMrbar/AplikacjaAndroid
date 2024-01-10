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

    @Test
    fun testWriteAppendAndRead() {
        val fileName = "test_file.txt"
        val items = listOf(
            "1;32re;6.12.2023;258;INCOME",
            "2;Samochód;3.3.2024;33.33;INCOME",
            "3;Samolot;10.12.2022;1000;INCOME"
        )
        val fileManager = FileManager(fileName)
        fileManager.writeStringItemsToFile(appContext, items)
        fileManager.appendToFile("testAppend;7.12.2023;258.13;HOME", appContext)
        val result = fileManager.readItemsFromFile(appContext)
        assertEquals(listOf(
            ItemData(1, 2, "32re", BigDecimal(258), "6.12.2023" ),
            ItemData(2, 2, "Samochód", BigDecimal("33.33"), "3.3.2024"),
            ItemData(3, 2, "Samolot", BigDecimal(1000), "10.12.2022"),
            ItemData(4, 4, "testAppend", BigDecimal("258.13"), "7.12.2023")
        ), result)
    }

    @Test
    fun testDelete() {
        val fileName = "test_file.txt"
        val items = listOf(
            "1;32re;6.12.2023;258;INCOME",
            "2;Samochód;3.3.2024;33.33;INCOME",
            "3;Samolot;10.12.2022;1000;INCOME"
        )
        val fileManager = FileManager(fileName)
        fileManager.writeStringItemsToFile(appContext, items)
        fileManager.deleteItemFromFile(appContext, 1)
        val result = fileManager.readItemsFromFile(appContext)
        assertEquals(listOf(
            ItemData(1, 2, "32re", BigDecimal(258), "6.12.2023" ),
            ItemData(3, 2, "Samolot", BigDecimal(1000), "10.12.2022")
        ), result)
    }

    @Test
    fun testCouner() {
        val counter = Counter(appContext)
        val fileName = "expenses.txt"
        val fileManager = FileManager(fileName)
        fileManager.writeStringItemsToFile(appContext, listOf(
                "1;32re;6.1.2024;258;INCOME",
                "2;Samochód;3.1.2024;33.33;INCOME",
                "3;Samolot;10.1.2022;1000;INCOME",
                "4;Dom;3.4.2024;300;INCOME"
        ))
        assertEquals(counter.countExpensesThisMonth(), BigDecimal("291.33"))
    }

    @Test
    fun testCouner1() {
        val counter = Counter(appContext)
        val fileName = "expensesPlan.txt"
        val fileManager = FileManager(fileName)
        fileManager.writeStringItemsToFile(appContext, listOf(
            "1;32re;CO MIESIĄC;258;INCOME",
            "2;Samochód;CO 3 MIESIĄCE;33.33;INCOME",
            "3;Samolot;CO MIESIĄC;1000;INCOME",
            "4;Dom;CO 12 MIESIĄCY;300;INCOME"
        ))
        assertEquals(counter.countExpensesPlan(), BigDecimal("1591.33"))
    }
}