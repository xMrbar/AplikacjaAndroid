package com.example.aplikacjaandroid.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun PieChartView(aktualne: String, planowe: String) {
    var aktualneFloat = aktualne.substring(0, aktualne.length - 2).toFloat()
    val planoweFloat = planowe.substring(0, planowe.length - 2).toFloat()

    if(aktualneFloat < 0) {
        aktualneFloat = 0F
    }
    else if(aktualneFloat > planoweFloat) {
        aktualneFloat = planoweFloat
    }

    val procentAktualnych: Float = (aktualneFloat / planoweFloat) * 100
    val procentPlanowych: Float = 100 - procentAktualnych

    val slices = listOf(
        PieChartData.Slice(value = procentAktualnych, color = MaterialTheme.colorScheme.primary, label = ""),
        PieChartData.Slice(value = procentPlanowych, color = Color.White, label = "")
    )

    val pieChartConfig = PieChartConfig(
        startAngle = 0f,
        showSliceLabels = false,
        isSumVisible = false,
        sumUnit = "",
        isClickOnSliceEnabled = false,
        labelVisible = false,
        activeSliceAlpha = 1f,
        inActiveSliceAlpha = 1f,
    )

    val pieChartData = PieChartData(slices, PlotType.Donut)

    DonutPieChart(
        modifier = Modifier
            .width(260.dp)
            .height(260.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    ) { slice -> }
}