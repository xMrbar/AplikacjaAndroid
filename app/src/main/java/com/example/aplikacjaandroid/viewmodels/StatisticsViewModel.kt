package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.model.StatisticsAdapter
import com.example.aplikacjaandroid.model.StatisticsItem
import com.example.aplikacjaandroid.services.StatisticsServices
import com.example.aplikacjaandroid.services.TimeInterval
import com.example.aplikacjaandroid.services.TimeIntervalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
    val currentTimeIntervalsType: TimeIntervalType? = null,

    val currentTimeInterval: TimeInterval? = TimeInterval(TimeIntervalType.Today.toString(), LocalDate.now(), LocalDate.now()),

    val timeIntervalsList: List<TimeInterval>? = null,

    val statisticsItemList: List<StatisticsItem>? = null,

    val total: BigDecimal = BigDecimal("0"),

    )

/*
 * ViewModel for StatisticsScreen
 */

class StatisticsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUIState())
    val uiState: StateFlow<StatisticsUIState> = _uiState.asStateFlow()
    private final val TAG = "StatisticsViewModel"

    // configures adapter and gets initial data from it
    // Initial state is: Expense Statistics for Today
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

        val todayExpensesList =
            if (!adapter?.getAllExpenses().isNullOrEmpty())
                adapter?.let{it.getExpensesStatisticsFromPeriod(LocalDate.now(), LocalDate.now())}
            else
                listOf()

        val newTotal =
            if (!todayExpensesList.isNullOrEmpty())
                StatisticsServices.calculateTotal(todayExpensesList!!)
            else
                BigDecimal("0")

        // updating ui state
        _uiState.update{ _uiState.value.copy(
            earliestExpenseDate = earliestExpanse,
            earliestRevenueDate = earliestRevenue,
            currentTimeIntervalsType = TimeIntervalType.Today,
            statisticsItemList = todayExpensesList,
            total = newTotal

        )}

        updateTimeIntervalsList()
        updateStatistics()
        Log.d(TAG, "StatisticsViewModel sat to initial state.")

    }

    // configuring new adapter
    private fun updateStatisticsAdapter(context: Context){
        _uiState.update{ _uiState.value.copy(statisticsAdapter = StatisticsAdapter(context))}
        _uiState.value.statisticsAdapter?.init()
        Log.d(TAG, "New StatisticsAdapter configured.")
    }


    // updates currentTimeIntervalType
    fun updateTimeIntervalType(selectedIntervalType: TimeIntervalType){

        // today and current week have only one TimeInterval
        // Dropdown menu set to non-expandable
        if(selectedIntervalType == TimeIntervalType.Today || selectedIntervalType == TimeIntervalType.CurrentWeek)
            _uiState.update{ _uiState.value.copy(
                currentTimeIntervalsType = selectedIntervalType,
                isTimeIntervalDropdownExpandable = false
            )}
        else
            _uiState.update{ _uiState.value.copy(
                currentTimeIntervalsType = selectedIntervalType,
                isTimeIntervalDropdownExpandable = true
            )}

        updateTimeIntervalsList()
        updateStatistics()
        Log.d(TAG, "TimeIntervalType updated for: ${selectedIntervalType.label}.")
    }

    // updates TimeIntervalsList using StatisticsServices
    fun updateTimeIntervalsList(){

        val newList: List<TimeInterval> = when(uiState.value.currentTimeIntervalsType){

            // only one TimeInterval
            TimeIntervalType.Today -> {
                val dateFormat = DateTimeFormatter.ofPattern("[dd].[MM].yyyy")
                val now = LocalDate.now()
                listOf<TimeInterval>(TimeInterval(now.format(dateFormat),now,now))
            }

            // only one TimeInterval
            TimeIntervalType.CurrentWeek -> listOf(StatisticsServices.getWeekRange(LocalDate.now()))


            TimeIntervalType.Month -> {
                if(uiState.value.isExpansesMode)
                    StatisticsServices.getMonthsSince(uiState.value.earliestExpenseDate!!)
                else
                    StatisticsServices.getMonthsSince(uiState.value.earliestRevenueDate!!)
            }

            TimeIntervalType.Year -> {
                if(uiState.value.isExpansesMode)
                    StatisticsServices.getYearsSince(uiState.value.earliestExpenseDate!!)
                else
                    StatisticsServices.getYearsSince(uiState.value.earliestRevenueDate!!)
            }
            else -> listOf()

        }

        //updating state with first position from new list
        _uiState.update { _uiState.value.copy(
            timeIntervalsList = newList,
            currentTimeInterval = newList[0]
            )
        }
        Log.d(TAG, "TimeIntervalsList updated.")
    }


    // updates selected TimeInterval
    fun updateTimeInterval(selectedInterval: TimeInterval){
        _uiState.update{ _uiState.value.copy(
            currentTimeInterval = selectedInterval,
        )}
        updateStatistics()
        Log.d(TAG, "Current TimeInterval updated for: ${selectedInterval.label}")
    }



    // changes mode form expenses to revenues and vice versa
    fun changeMode(){
        _uiState.update { _uiState.value.copy(isExpansesMode = !uiState.value.isExpansesMode) }
        updateTimeIntervalsList()
        updateStatistics()
        Log.d(TAG, "Mode changed. IsExpensesMode: ${uiState.value.isExpansesMode}")
    }

    private fun updateStatistics(){
        updateStatisticsList()
        updateTotal()
        Log.d(TAG, "Statistics updated.")
    }

    // updates statistics items  list using StatisticsAdapter
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
                Log.d(TAG, "StatisticsList updated. Mode: Expenses. List size: ${uiState.value.statisticsItemList?.size}")

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
            Log.d(TAG, "StatisticsList updated. Mode: Revenues. List size: ${uiState.value.statisticsItemList?.size}")
        }

    }


    // updates total based on current list of statistics item using StatisticsServices
    private fun updateTotal(){

        val newTotal = if (!uiState.value.statisticsItemList.isNullOrEmpty())  StatisticsServices.calculateTotal(uiState.value.statisticsItemList!!)
        else BigDecimal("0")

        _uiState.update{ _uiState.value.copy(
            total = newTotal
        )}
        Log.d(TAG, "Total updated. New value: $newTotal")

    }




}