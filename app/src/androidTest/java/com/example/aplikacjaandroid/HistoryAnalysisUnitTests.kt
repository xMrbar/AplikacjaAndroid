package com.example.aplikacjaandroid

import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplikacjaandroid.model.ItemData
import com.example.aplikacjaandroid.viewmodels.HistoryAnalysisViewModel
import org.junit.Test
import java.math.BigDecimal
class HistoryAnalysisUnitTests {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun testCalculateTotal() {
        CreateFile(context = appContext)
        val haVM = HistoryAnalysisViewModel()
        val itemList = listOf(
            ItemData(1, 2, "car1", BigDecimal(28), "6.12.2023" ),
            ItemData(3, 2, "car2", BigDecimal(10), "11.02.2022"),
            ItemData(1, 2, "car3", BigDecimal(8), "7.11.2023" ),
            ItemData(3, 2, "car4", BigDecimal(24), "10.12.2022"))
        assert(haVM.calculateTotal(itemList) == BigDecimal(70))
    }
    @Test
    fun testUpdateMonth() {
        CreateFile(context = appContext)
        val haVM = HistoryAnalysisViewModel()
        val haUI = haVM.uiState
        haVM.updateMonth("Luty")
        assert(haUI.value.chosenMonth=="Luty")
    }
    @Test
    fun testUpdateYear() {
        CreateFile(context = appContext)
        val haVM = HistoryAnalysisViewModel()
        val haUI = haVM.uiState
        haVM.updateYear("2022")
        assert(haUI.value.chosenYear=="2022")
    }

}