package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.data.HistoryAdapter
import com.example.aplikacjaandroid.data.ItemData
import com.example.aplikacjaandroid.ui.components.polishMonthToNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal


data class HistoryAnalysisUIState(

    val historyAdapter: HistoryAdapter? = null,

    val isMonthMode: Boolean = true,

    val chosenYear: String = "2023",

    val chosenMonth: String = "Grudzień",

    val revenues: BigDecimal = BigDecimal("0"),

    val expenses: BigDecimal = BigDecimal("0"),

    val revenuesItemList: List<ItemData>? = null,

    val expensesItemList: List<ItemData>? = null,

    val months: List<String>? =  listOf("Styczeń", "Luty","Marzec","Kwiecień","Maj",
        "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"),
    val years: List<String>? = listOf("2021", "2022", "2023","2024")


)

class HistoryAnalysisViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryAnalysisUIState())
    val uiState: StateFlow<HistoryAnalysisUIState> = _uiState.asStateFlow()
    var expanded : Boolean = false

    fun updateExpanded(new_state: Boolean) {
        expanded = new_state
    }
    fun changeMode(){
        _uiState.update { _uiState.value.copy(isMonthMode = !uiState.value.isMonthMode) }
        updateAnalysis()
    }
    fun initializeAnalysis(context: Context){

        updateHistoryAdapter(context)
        updateHistoryLists()
        updateRevenuesAndExpenses()

    }
    fun updateHistoryAdapter(context: Context){
        _uiState.update{ _uiState.value.copy(historyAdapter = HistoryAdapter(context))}
        _uiState.value.historyAdapter?.init()
    }

    fun updateMonth(selectedMonth: String){
        _uiState.update{ _uiState.value.copy(
            chosenMonth = selectedMonth,
        )}
        updateAnalysis()
    }
    fun updateYear(selectedYear: String){
        _uiState.update{ _uiState.value.copy(
            chosenYear = selectedYear,
        )}
        updateAnalysis()
    }

    private fun updateAnalysis(){
        updateHistoryLists()
        updateRevenuesAndExpenses()
    }
    private fun calculateTotal(list: List<ItemData>): BigDecimal{

        return list.map { it.amount }.reduce { acc, amount -> acc + amount }
    }

    private fun updateRevenuesAndExpenses(){

        val calculatedExpenses = if (!uiState.value.expensesItemList.isNullOrEmpty())  calculateTotal(uiState.value.expensesItemList!!)
        else BigDecimal("0")
        val calculatedRevenues = if (!uiState.value.revenuesItemList.isNullOrEmpty())  calculateTotal(uiState.value.revenuesItemList!!)
        else BigDecimal("0")
        _uiState.update{ _uiState.value.copy(
            expenses = calculatedExpenses,
            revenues = calculatedRevenues,
        )}
    }

    private fun updateHistoryLists(){

        if (uiState.value.isMonthMode) {
            _uiState.update {
                _uiState.value.copy(
                    revenuesItemList = uiState.value.historyAdapter?.getRevenuesFromMonth(polishMonthToNumber(_uiState.value.chosenMonth)!!, (_uiState.value.chosenYear!!).toInt()),
                    expensesItemList = uiState.value.historyAdapter?.getExpensesFromMonth(polishMonthToNumber(_uiState.value.chosenMonth)!!, (_uiState.value.chosenYear!!).toInt())
                    )
            }
        }
        else {
            _uiState.update {
                _uiState.value.copy(
                    revenuesItemList = uiState.value.historyAdapter?.getRevenuesFromYear((_uiState.value.chosenYear!!).toInt()),
                    expensesItemList = uiState.value.historyAdapter?.getExpensesFromYear((_uiState.value.chosenYear!!).toInt())
                )

            }
        }
    }
}