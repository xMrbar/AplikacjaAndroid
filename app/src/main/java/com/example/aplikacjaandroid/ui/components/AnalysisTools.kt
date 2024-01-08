package com.example.aplikacjaandroid.ui.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.selectfield.SelectField
import kotlinx.coroutines.flow.MutableStateFlow
import co.yml.charts.axis.DataCategoryOptions
import androidx.compose.foundation.layout.*
import co.yml.charts.axis.AxisData
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarStyle

import co.yml.charts.ui.barchart.models.*

fun polishMonthToNumber(monthName: String): Int? {
    val monthMap = mapOf(
        "styczeń" to 1,
        "luty" to 2,
        "marzec" to 3,
        "kwiecień" to 4,
        "maj" to 5,
        "czerwiec" to 6,
        "lipiec" to 7,
        "sierpień" to 8,
        "wrzesień" to 9,
        "październik" to 10,
        "listopad" to 11,
        "grudzień" to 12
    )

    return monthMap[monthName.lowercase()]
}

@Composable
fun BarchartWithSolidBars() {
    val maxRange = 50
    val barData = DataUtils.getBarChartData(50, maxRange, BarChartType.VERTICAL, DataCategoryOptions())
    val yStepSize = 10

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barData.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .startDrawPadding(48.dp)
        .labelData { index -> barData[index].label }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val barChartData = BarChartData(
        chartData = barData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            paddingBetweenBars = 20.dp,
            barWidth = 25.dp
        ),
        showYAxis = true,
        showXAxis = true,
        horizontalExtraSpace = 10.dp,
    )
    BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun mySelectBox(options: List<String>,
                text: MutableStateFlow<String>)
{
    var expanded by remember { mutableStateOf(false) }

    Column {
        SelectField(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
            text = text.value,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        text.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}
