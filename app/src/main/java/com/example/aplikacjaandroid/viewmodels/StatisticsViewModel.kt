package com.example.aplikacjaandroid.viewmodels

import android.content.Context
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
    val currentTimeIntervalsLength: TimeIntervalType? = null,

    val currentTimeInterval: TimeInterval? = TimeInterval(TimeIntervalType.Today.toString(), LocalDate.now(), LocalDate.now()),

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
        val newTotal = if (!todayExpensesList.isNullOrEmpty())  StatisticsServices.calculateTotal(todayExpensesList!!)
        else BigDecimal("0")

        _uiState.update{ _uiState.value.copy(
            earliestExpenseDate = earliestExpanse,
            earliestRevenueDate = earliestRevenue,
            currentTimeIntervalsLength = TimeIntervalType.Today,
            statisticsItemList = todayExpensesList,
            total = newTotal

        )}

        updateTimeIntervalsList()
        updateStatistics()

    }

    fun updateStatisticsAdapter(context: Context){
        _uiState.update{ _uiState.value.copy(statisticsAdapter = StatisticsAdapter(context))}
        _uiState.value.statisticsAdapter?.init()
    }

    fun updateTimeIntervalLength(selectedIntervalLength: TimeIntervalType){

        if(selectedIntervalLength == TimeIntervalType.Today || selectedIntervalLength == TimeIntervalType.CurrentWeek)
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

            TimeIntervalType.Today -> {
                val dateFormat = DateTimeFormatter.ofPattern("[dd].[MM].yyyy")
                val now = LocalDate.now()
                listOf<TimeInterval>(TimeInterval(now.format(dateFormat),now,now))
            }

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