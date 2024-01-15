package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.model.HistoryAdapter
import com.example.aplikacjaandroid.model.ItemData
import com.example.aplikacjaandroid.ui.components.BarGroup
import com.example.aplikacjaandroid.ui.components.BarInfo
import com.example.aplikacjaandroid.ui.components.numberToMonth
import com.example.aplikacjaandroid.ui.components.polishMonthToNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.YearMonth
import java.time.Year


data class HistoryAnalysisUIState(

    val historyAdapter: HistoryAdapter? = null,

    val isMonthMode: Boolean = true,

    val chosenYear: String = Year.now().value.toString(),

    val chosenMonth: String = numberToMonth(YearMonth.now().monthValue!!)!!,

    val revenues: BigDecimal = BigDecimal("0"),

    val expenses: BigDecimal = BigDecimal("0"),

    val revenuesItemList: List<ItemData>? = null,

    val expensesItemList: List<ItemData>? = null,

    val months: List<String> =  listOf("Styczeń", "Luty","Marzec","Kwiecień","Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"),

    val years: List<String> = listOf("2021", "2022", "2023","2024"),

    val graphData: List<BarGroup> = listOf(),

    val maxMonthlyValue: BigDecimal = BigDecimal("0"),

    val maxYearlyValue: BigDecimal = BigDecimal("0"),

    val meanRevenue: BigDecimal = BigDecimal("0"),

    val meanExpense: BigDecimal = BigDecimal("0"),
)

class HistoryAnalysisViewModel : ViewModel() {

    var isYearsListExpanded by mutableStateOf(false)
    var isMonthsListExpanded by mutableStateOf(false)

    private val _uiState = MutableStateFlow(HistoryAnalysisUIState())
    val uiState: StateFlow<HistoryAnalysisUIState> = _uiState.asStateFlow()


    fun updateExpandedYears(newstate: Boolean) {
        isYearsListExpanded = newstate
    }

    fun updateExpandedMonths(newstate: Boolean) {
        isMonthsListExpanded = newstate
    }
    fun changeMode(){
        _uiState.update { _uiState.value.copy(isMonthMode = !uiState.value.isMonthMode) }
        updateAnalysis()
    }
    fun initializeAnalysis(context: Context){

        updateHistoryAdapter(context)
        updateHistoryLists()
        updateRevenuesAndExpenses()
        updategraphData()

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
        updategraphData()
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
    private fun updategraphData(){
        val newgraphData: MutableList<BarGroup> = mutableListOf()
        val newgraphInfo: MutableList<BarInfo> = mutableListOf()
        var newMeanRevenue: BigDecimal = BigDecimal("0")
        var newMeanExpense: BigDecimal = BigDecimal("0")

        if (uiState.value.isMonthMode) {
            var maxMonthlyValue  = BigDecimal("1")
            for (i in 0..5) {
                var analyzedYear =  (_uiState.value.chosenYear!!).toInt()
                var analyzedMonth = ((polishMonthToNumber(_uiState.value.chosenMonth)!!).toInt() - (5-i))
                if (analyzedMonth<1) {
                    analyzedYear -= 1
                    analyzedMonth += 12
                }
                val revenuesList = uiState.value.historyAdapter?.getRevenuesFromMonth(analyzedMonth, analyzedYear)
                val expensesList = uiState.value.historyAdapter?.getExpensesFromMonth(analyzedMonth, analyzedYear)
                val totalExpenses = if (!expensesList.isNullOrEmpty())  calculateTotal(expensesList!!)
                else BigDecimal("0")
                val totalRevenues = if (!revenuesList.isNullOrEmpty())  calculateTotal(revenuesList!!)
                else BigDecimal("0")
                if (totalExpenses>maxMonthlyValue){maxMonthlyValue=totalExpenses}
                if (totalRevenues>maxMonthlyValue){maxMonthlyValue=totalRevenues}
                newMeanRevenue+=totalRevenues
                newMeanExpense+=totalExpenses
                val bar = BarInfo(
                    label = "$analyzedYear/$analyzedMonth",
                    values = listOf(
                        totalExpenses,
                        totalRevenues,
                    )
                )
                newgraphInfo.add(bar)
            }
            for (item in newgraphInfo) {
                val bar = BarGroup(
                    label = item.label,
                    values = listOf(
                        ((item.values[0]).multiply(BigDecimal(100)).divide(maxMonthlyValue, 2, RoundingMode.HALF_UP)).toInt() to Color(126, 188, 222, 255),
                        ((item.values[1]).multiply(BigDecimal(100)).divide(maxMonthlyValue, 2, RoundingMode.HALF_UP)).toInt() to Color(145, 146, 222, 255),
                    )
                )
                newgraphData.add(bar)
            }
            newMeanRevenue=newMeanRevenue.divide(BigDecimal("6"), 2, RoundingMode.HALF_UP)
            newMeanExpense=newMeanRevenue.divide(BigDecimal("6"), 2, RoundingMode.HALF_UP)

        }
        else {
            var maxYearlyValue  = BigDecimal("1")
            for (i in 0..2) {


                val analyzedYear = (_uiState.value.chosenYear!!).toInt() - (2-i)
                val revenuesList = uiState.value.historyAdapter?.getRevenuesFromYear(analyzedYear);
                val expensesList = uiState.value.historyAdapter?.getExpensesFromYear(analyzedYear)
                val totalExpenses = if (!expensesList.isNullOrEmpty())  calculateTotal(expensesList!!)
                else BigDecimal("0")
                val totalRevenues = if (!revenuesList.isNullOrEmpty())  calculateTotal(revenuesList!!)
                else BigDecimal("0")
                if (totalExpenses>maxYearlyValue){maxYearlyValue=totalExpenses}
                if (totalRevenues>maxYearlyValue){maxYearlyValue=totalRevenues}
                newMeanRevenue+=totalRevenues
                newMeanExpense+=totalExpenses
                val bar = BarInfo(
                    label = "$analyzedYear",
                    values = listOf(
                        totalExpenses,
                        totalRevenues,
                    )
                )
                newgraphInfo.add(bar)
            }
            for (item in newgraphInfo) {
                val bar = BarGroup(
                    label = item.label,
                    values = listOf(
                        ((item.values[0]).multiply(BigDecimal(100)).divide(maxYearlyValue, 2, RoundingMode.HALF_UP)).toInt() to Color(126, 188, 222, 255),
                        ((item.values[1]).multiply(BigDecimal(100)).divide(maxYearlyValue, 2, RoundingMode.HALF_UP)).toInt() to Color(145, 146, 222, 255),
                    )
                )
                newgraphData.add(bar)
            }
            newMeanRevenue=newMeanRevenue.divide(BigDecimal("3"), 2, RoundingMode.HALF_UP)
            newMeanExpense=newMeanRevenue.divide(BigDecimal("3"), 2, RoundingMode.HALF_UP)
        }
        _uiState.update {
            _uiState.value.copy(
                graphData = newgraphData,
                meanExpense = newMeanExpense,
                meanRevenue = newMeanRevenue
            )
        }
    }

}