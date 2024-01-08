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


    fun getPieChartData(list: List<StatisticsItem>, total: BigDecimal): PieChartData {

        val slicesList: MutableList<PieChartData.Slice> = mutableListOf()

        list.forEach() { item ->

            val sliceValue = (item.value.toFloat() / total.toFloat()) * 100
            val slice = PieChartData.Slice(item.category.label, sliceValue, item.category.color)
            slicesList.add(slice)

        }

        return PieChartData(
            slices = slicesList,
            plotType = PlotType.Pie
        )
    }

    fun getPieChartConfig(): PieChartConfig{

        return PieChartConfig(
            isAnimationEnable = false,
            showSliceLabels = true,
            backgroundColor = Color(0, 0, 0, 255),
            isClickOnSliceEnabled = false,
            sliceLabelTextColor = Color(0, 0, 0, 255)
        )
    }



}