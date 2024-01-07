package com.example.aplikacjaandroid.ui.screens
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.viewmodels.HistoryAnalysisViewModel
import co.yml.charts.axis.DataCategoryOptions
import androidx.compose.foundation.layout.*
import co.yml.charts.axis.AxisData
import co.yml.charts.common.utils.DataUtils

import com.example.aplikacjaandroid.underlinedtext.UnderlinedText
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarStyle

import co.yml.charts.ui.barchart.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.aplikacjaandroid.ui.components.mySelectBox


@Composable
private fun BarchartWithSolidBars() {
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
@Composable
@Preview
fun HistoryAnalysisPreview(){
    HistoryAnalysisScreen(modifier = Modifier
        .fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryAnalysisScreen(modifier : Modifier = Modifier,
                          historyAnalysisViewModel: HistoryAnalysisViewModel = viewModel()
){

    val historyAnalysisUIState by historyAnalysisViewModel.uiState.collectAsState()

    val textColor = Color(ContextCompat.getColor(LocalContext.current, R.color.backgroud))
    val context: Context = LocalContext.current

    /*
    LaunchedEffect(historyAnalysisViewModel) {
        historyAnalysisViewModel.getInitialData(context)
    }*/

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp), verticalArrangement = Arrangement.SpaceAround

    ) {
        LabelLarge(text = stringResource(id = R.string.button5Text))

        mySelectBox(options = listOf("2021", "2022", "2023"), MutableStateFlow("Rok"))

        if(historyAnalysisUIState.isMonthMode) {
            mySelectBox(
                options = listOf(
                    "Czerwiec",
                    "Lipiec",
                    "Sierpień",
                    "Wrzesień",
                    "Październik",
                    "Listopad",
                    "Gruzień"
                ), MutableStateFlow("Miesiąc")
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    BorderStroke(
                        1.dp,
                        Color(ContextCompat.getColor(LocalContext.current, R.color.outline))
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))
                .padding(4.dp)

        ) {

            UnderlinedText(text = "Grudzień 2023", modifier = Modifier.fillMaxWidth())
            BarchartWithSolidBars()
            Spacer(
                modifier = Modifier
                    .height(0.dp)
                    .background(Color.Gray)
            )

        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    Color(ContextCompat.getColor(LocalContext.current, R.color.primary)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Text(text = "5988.19zł", color = Color.Black)
            /*com.example.aplikacjaandroid.selectfield.Text(text = "5988.19zł")*/
            /*com.example.aplikacjaandroid.selectfield.Text(text = "WYDATKI")*/
            androidx.compose.material3.Text(text = "WYDATKI", color = Color.Black)
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    Color(ContextCompat.getColor(LocalContext.current, R.color.primary)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*
            com.example.aplikacjaandroid.selectfield.Text(text = "7860.00zł")
            com.example.aplikacjaandroid.selectfield.Text(text = "PRZYCHODY")*/
            androidx.compose.material3.Text(text = "7860.00zł", color = Color.Black)
            androidx.compose.material3.Text(text ="PRZYCHODY", color = Color.Black)

        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
            if(historyAnalysisUIState.isMonthMode){
                ButtonNarrow(
                    text = stringResource(id = R.string.miesiac),
                    onClick = {},
                    property1 = Property1.Variant2
                )
                ButtonNarrow(
                    text = stringResource(id = R.string.rok),
                    onClick = {historyAnalysisViewModel.changeMode()},
                )
            }
            else{
                ButtonNarrow(
                    text = stringResource(id = R.string.miesiac),
                    onClick = {historyAnalysisViewModel.changeMode()},

                    )
                ButtonNarrow(
                    text = stringResource(id = R.string.rok),
                    onClick = {},
                    property1 = Property1.Variant2
                )

            }

        }






    }
}
