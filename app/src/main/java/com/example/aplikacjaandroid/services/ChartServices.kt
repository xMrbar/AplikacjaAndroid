package com.example.aplikacjaandroid.services

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.aplikacjaandroid.model.StatisticsItem
import java.math.BigDecimal


/*
 * This object provides functions for drawing pie charts
 */

object ChartServices {

    // return PieChartData based on a list of StatisticsItems
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


    // returns configuration object PieChartConfig with some hardcoded values
    fun getPieChartConfig(): PieChartConfig{


        // colors have to be passed like that because otherwise they appear incorrectly for some reason
        return PieChartConfig(
            isAnimationEnable = false,
            showSliceLabels = true,
            backgroundColor = Color(0, 0, 0, 255),
            isClickOnSliceEnabled = false,
            sliceLabelTextColor = Color(0, 0, 0, 255)
        )
    }



}