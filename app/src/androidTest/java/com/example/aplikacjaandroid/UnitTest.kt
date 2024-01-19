package com.example.aplikacjaandroid

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplikacjaandroid.viewmodels.AccountBalanceViewModel
import com.example.aplikacjaandroid.viewmodels.AddNewExpenseViewModel
import org.junit.Test
import java.math.BigDecimal

class UnitTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testonClick() {
        CreateFile(context = appContext)
        var accVM = AccountBalanceViewModel(appContext)
        accVM.onClick(5)
        assert(5 == accVM.selectedIndex.value)
    }

    @Test
    fun testonClick1() {
        CreateFile(context = appContext)
        var accVM = AccountBalanceViewModel(appContext)
        accVM.onClick(4)
        accVM.onClick(4)
        assert(-1 == accVM.selectedIndex.value)
    }

    @Test
    fun testResetIndex() {
        CreateFile(context = appContext)
        var accVM = AccountBalanceViewModel(appContext)
        accVM.onClick(4)
        accVM.resetIndex()
        assert(-1 == accVM.selectedIndex.value)
    }

    @Test
    fun testUpdateTytul() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateTytul("ABCD")
        assert("ABCD" == aneVM.tytul)
    }

    @Test
    fun testUpdateTytul1() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateTytul("ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD")
        assert("" == aneVM.tytul)
    }

    @Test
    fun testUpdateKwota() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateKwota("4.44")
        assert(aneVM.kwota == BigDecimal("4.44"))
    }

    @Test
    fun testUpdateKategoria() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateKategoria("Test")
        assert("Test" == aneVM.selectedOption2)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testUpdateDate() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        val d = DatePickerState(null, null, IntRange(1970, 2025), DisplayMode.Picker)
        aneVM.updateData(d)
        assert(aneVM.selectedOption1 == "")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testUpdateDate1() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        val d = DatePickerState(1, 1, IntRange(1970, 2025), DisplayMode.Picker)
        aneVM.updateData(d)
        assert(aneVM.selectedOption1 == "1.1.1970")
    }

    @Test
    fun testExpanded() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateExpanded(true)
        assert(aneVM.expanded)
    }

    @Test
    fun testExpanded1() {
        CreateFile(context = appContext)
        val aneVM = AddNewExpenseViewModel()
        aneVM.updateExpanded1(false)
        assert(!aneVM.expanded)
    }
}