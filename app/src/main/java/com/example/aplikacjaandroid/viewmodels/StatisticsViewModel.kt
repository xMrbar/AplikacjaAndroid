package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import android.util.Log
import androidx.collection.emptyLongSet
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

    val timeIntervalsList: List<TimeInterval>? = null,

    val statisticsItemList: List<StatisticsItem>? = null,

    val total: BigDecimal = BigDecimal("0"),

    )

class StatisticsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUIState())
    val uiState: StateFlow<StatisticsUIState> = _uiState.asStateFlow()

    fun getInitialData(context: Context){

        updateStatisticsAdapter(context)

        val adapter = _uiState.value.statisticsAdapter
        val earliestExpanse: LocalDate? =
            if (!adapter?.getAllExpenses().isNullOrEmpty())
                adapter?.let { StatisticsServices.getEarliestDate(it.getAllExpenses()) }
            else LocalDate.now()
        val earliestRevenue: LocalDate? =
            if (!adapter?.getAllRevenues().isNullOrEmpty())
                adapter?.let { StatisticsServices.getEarliestDate(it.getAllRevenues()) }
            else LocalDate.now()
        val todayExpensesList = if (!adapter?.getAllExpenses().isNullOrEmpty())
            adapter?.let{it.getExpensesStatisticsFromPeriod(LocalDate.now(), LocalDate.now())}
        else listOf()
        val newTotal = if (!uiState.value.statisticsItemList.isNullOrEmpty())  StatisticsServices.calculateTotal(uiState.value.statisticsItemList!!)
        else BigDecimal("0")

        _uiState.update{ _uiState.value.copy(
            earliestExpenseDate = earliestExpanse,
            earliestRevenueDate = earliestRevenue,
            currentTimeIntervalsLength = TimeIntervalsLength.Today,
            statisticsItemList = todayExpensesList,
            total = newTotal

        )}

        Log.d("TEST", _uiState.toString())
        Log.d("EXPENSES", uiState.value.earliestExpenseDate.toString())
        Log.d("REVENUES", uiState.value.earliestRevenueDate.toString())

    }

    fun updateStatisticsAdapter(context: Context){
        _uiState.update{ _uiState.value.copy(statisticsAdapter = StatisticsAdapter(context))}
        _uiState.value.statisticsAdapter?.init()
    }

    fun updateTimeIntervalLength(selectedIntervalLength: TimeIntervalsLength){

        if(selectedIntervalLength == TimeIntervalsLength.Today || selectedIntervalLength == TimeIntervalsLength.CurrentWeek)
            _uiState.update{ _uiState.value.copy(
                currentTimeIntervalsLength = selectedIntervalLength,
                isTimeIntervalDropdownExpandable = false
            )}
        else
            _uiState.update{ _uiState.value.copy(
                currentTimeIntervalsLength = selectedIntervalLength,
                isTimeIntervalDropdownExpandable = true
            )}

        updateTimeIntervalsList()
        updateStatistics()
    }

    fun updateTimeIntervalsList(){

        val newList: List<TimeInterval> = when(uiState.value.currentTimeIntervalsLength){

            TimeIntervalsLength.Today -> {
                val now = LocalDate.now()
                listOf<TimeInterval>(TimeInterval(now.toString(),now,now))
            }

            TimeIntervalsLength.CurrentWeek -> listOf(StatisticsServices.getWeekRange(LocalDate.now()))
            TimeIntervalsLength.Month -> {
                if(uiState.value.isExpansesMode)
                    StatisticsServices.getMonthsSince(uiState.value.earliestExpenseDate!!)
                else
                    StatisticsServices.getMonthsSince(uiState.value.earliestRevenueDate!!)
            }
            TimeIntervalsLength.Year -> {
                if(uiState.value.isExpansesMode)
                    StatisticsServices.getYearsSince(uiState.value.earliestExpenseDate!!)
                else
                    StatisticsServices.getYearsSince(uiState.value.earliestRevenueDate!!)
            }
            else -> listOf()

        }
        _uiState.update { _uiState.value.copy(
            timeIntervalsList = newList,
            currentTimeInterval = newList[0]
            )
        }

    }

    fun updateTimeInterval(selectedInterval: TimeInterval){
        _uiState.update{ _uiState.value.copy(
            currentTimeInterval = selectedInterval,
        )}
        updateStatistics()
    }



    fun changeMode(){
        _uiState.update { _uiState.value.copy(isExpansesMode = !uiState.value.isExpansesMode) }
        updateTimeIntervalsList()
        updateStatistics()
    }

    private fun updateStatistics(){
        updateStatisticsList()
        updateTotal()
    }

    private fun updateStatisticsList(){

        val currentInterval = uiState.value.currentTimeInterval
        if (currentInterval != null) {
            if (uiState.value.isExpansesMode) {

                _uiState.update {
                    _uiState.value.copy(
                        statisticsItemList =
                        uiState.value.statisticsAdapter?.getExpensesStatisticsFromPeriod(
                            currentInterval.startDate,
                            currentInterval.endDate
                        )
                    )
                }

            } else {
                _uiState.update {
                    _uiState.value.copy(
                        statisticsItemList =
                        uiState.value.statisticsAdapter?.getRevenuesStatisticsFromPeriod(
                            currentInterval.startDate,
                            currentInterval.endDate
                        )
                    )
                }

            }
        }


    }

    private fun updateTotal(){

        val newTotal = if (!uiState.value.statisticsItemList.isNullOrEmpty())  StatisticsServices.calculateTotal(uiState.value.statisticsItemList!!)
        else BigDecimal("0")

        _uiState.update{ _uiState.value.copy(
            total = newTotal
        )}

    }

    private fun setInterval(selectedInterval: TimeInterval){

        _uiState.update{ _uiState.value.copy(
            currentTimeInterval = selectedInterval
        )}
        updateStatistics()
    }






}