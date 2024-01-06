package com.example.aplikacjaandroid.services

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.data.StatisticsItem
import java.math.BigDecimal
import kotlin.math.absoluteValue

object ChartServices {


    fun getPieChartData(list: List<StatisticsItem>, total: BigDecimal): PieChartData{

        val slicesList: MutableList<PieChartData.Slice> = mutableListOf()

        list.forEach(){

            val sliceValue = (it.value.toFloat()/total.toFloat())*100
            val slice = PieChartData.Slice(it.category.label,sliceValue, it.category.color)
            slicesList.add(slice)

        }

        val pieChartData = PieChartData(
            slices = slicesList,
            plotType = PlotType.Pie
        )
        return pieChartData
    }

    fun getPieChartConfig(): PieChartConfig{

        return PieChartConfig(
            isAnimationEnable = false,
            showSliceLabels = true,
            backgroundColor = Color(R.color.black.absoluteValue),
            isClickOnSliceEnabled = false,
            sliceLabelTextColor = Color(R.color.backgroud.absoluteValue)
        )
    }



}