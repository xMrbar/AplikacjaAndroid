package com.example.aplikacjaandroid.viewmodels

import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.services.StatisticsItem
import com.example.aplikacjaandroid.services.TimeIntervalsLength
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate


data class StatisticsUIState(

    val timeIntervalLength: TimeIntervalsLength  = TimeIntervalsLength.Today,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    // label to be displayed
    val timeIntervalLabel: String = "Today",
    //val timeInterval: TODO
    // for now  this are strings for testing purposes
    val timeIntervalsList: List<String> = listOf("Today", "Last Week", "TODO"),
    val total: Double = 0.0,
    val list: List<StatisticsItem> = listOf( StatisticsItem("Item1", "Value1"), StatisticsItem("Item2", "Value2")),
    val isTimeIntervalDropdownExpandable: Boolean = true
)

class StatisticsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUIState())
    val uiState: StateFlow<StatisticsUIState> = _uiState.asStateFlow()

    fun updateTimeIntervalLength( intervalLength: TimeIntervalsLength){
        //provisional time interval should be changed as well
        //for ui testing purposes
        _uiState.update{ _uiState.value.copy(timeIntervalLength = intervalLength)}
    }

    fun updateTimeInterval( interval: String){
        _uiState.update{ _uiState.value.copy(timeIntervalLabel = interval)}
    }

    private fun updateState(){

        //TODO
    }






}