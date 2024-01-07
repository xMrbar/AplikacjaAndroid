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


data class HistoryAnalysisUIState(

    val historyAnalysisAdapter: StatisticsAdapter? = null,

    val isMonthMode: Boolean = true,

    val total: BigDecimal = BigDecimal("0"),

    )

class HistoryAnalysisViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryAnalysisUIState())
    val uiState: StateFlow<HistoryAnalysisUIState> = _uiState.asStateFlow()

    fun changeMode(){
        _uiState.update { _uiState.value.copy(isMonthMode = !uiState.value.isMonthMode) }
        //updateTimeIntervalsList()
        //updateStatistics()
    }


}