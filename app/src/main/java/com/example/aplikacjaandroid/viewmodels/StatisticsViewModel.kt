package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.data.StatisticsAdapter
import com.example.aplikacjaandroid.data.StatisticsItem
import com.example.aplikacjaandroid.services.StatisticsServices
import com.example.aplikacjaandroid.services.TimeInterval
import com.example.aplikacjaandroid.services.TimeIntervalsLength
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.time.LocalDate


data class StatisticsUIState(

    //adapter with it's repos - must be initializes later because of needed context
    val statisticsAdapter: StatisticsAdapter? = null,

    // null because they need items from adapter
    val earliestExpenseDate: LocalDate? = null,
    val earliestRevenueDate: LocalDate? = null,

    // expenses or revenues mode flag
    val isExpansesMode: Boolean = true,

    // not expendable for today and currentWeek
    val isTimeIntervalDropdownExpandable: Boolean = false,

    // current configuration
    val currentTimeIntervalsLength: TimeIntervalsLength? = null,

    val currentTimeInterval: TimeInterval? = TimeInterval(TimeIntervalsLength.Today.toString(), LocalDate.now(), LocalDate.now()),

    val timeIntervalsList: List<String>? = null,

    val statisticsItemList: List<StatisticsItem>? = null,

    val total: BigDecimal = BigDecimal("0"),

)

class StatisticsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUIState())
    val uiState: StateFlow<StatisticsUIState> = _uiState.asStateFlow()

    fun getInitialData(context: Context){

        updateStatisticsAdapter(context)

        val adapter = _uiState.value.statisticsAdapter
        val earliestExpanse = adapter?.let { StatisticsServices.getEarliestDate(it.getAllExpenses()) }
        val earliestRevenue = adapter?.let { StatisticsServices.getEarliestDate(it.getAllRevenues()) }
        val todayExpensesList = adapter?.let{it.getExpensesStatisticsFromPeriod(LocalDate.now(), LocalDate.now())}

        _uiState.update{ _uiState.value.copy(
            earliestExpenseDate = earliestExpanse,
            earliestRevenueDate = earliestRevenue,
            currentTimeIntervalsLength = TimeIntervalsLength.Today,
            statisticsItemList = todayExpensesList,
            total = StatisticsServices.calculateTotal(todayExpensesList!!)

        )}

        Log.d("TEST", _uiState.toString())


    }

    fun updateStatisticsAdapter(context: Context){
        _uiState.update{ _uiState.value.copy(statisticsAdapter = StatisticsAdapter(context))}
        _uiState.value.statisticsAdapter?.init()
    }

    fun updateTimeIntervalLength( intervalLength: TimeIntervalsLength){
        //provisional time interval should be changed as well
        //for ui testing purposes
        _uiState.update{ _uiState.value.copy(currentTimeIntervalsLength = intervalLength)}
    }

//    fun updateTimeInterval( interval: String){
//        _uiState.update{ _uiState.value.copy(timeIntervalLabel = interval)}
//    }

    private fun updateState(){

        //TODO
    }






}