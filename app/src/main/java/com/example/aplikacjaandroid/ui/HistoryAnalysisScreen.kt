package com.example.aplikacjaandroid.ui

import android.graphics.Typeface
import androidx.activity.compose.setContent
import co.yml.charts.axis.DataCategoryOptions
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.selectfield.Text
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.drawBarGraph
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.getMaxElementInYAxis

import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.StackedBarChart
import co.yml.charts.ui.barchart.models.*
import kotlin.random.Random

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


class HistoryAnalysisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryAnalysisView()
                }
            }
        }
    }



}

@Composable
@Preview
fun HistoryAnalysisView() {
    HistoryAnalysis(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun HistoryAnalysis(modifier : Modifier = Modifier, context: Context) {
    //val localActivity = (LocalContext.current as? Activity)

    val innerModifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp), verticalArrangement = Arrangement.SpaceAround

    ) {
        LabelLarge(text = stringResource(id = R.string.button5Text))


        SelectField(
            modifier = Modifier.fillMaxWidth(),
            text = "Miesiąc",
        )

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

            /*
            Image(
                painter = painterResource(R.drawable.month_plot),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
            )*/
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

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            ButtonNarrow(
                text = "Miesiąc",
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
                property1 = Property1.Variant2
            )
            ButtonNarrow(
                text = "Rok",
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }
}
@Composable
fun ScrollableColumnExample() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Privacy Policy  ")
        Text(text = "Privacy Policy  ")
        Text(text = "Privacy Policy  ")
        Text(text = "Privacy Policy  ")
        Text(text = "Privacy Policy  ")
    }
}
/**
 * BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
 *             LineChart(
 *                 modifier = Modifier
 *                     .fillMaxWidth()
 *                     .height(300.dp),
 *                 lineChartData = data
 *             )
 * data class BarChartData(
 *     val chartData: List<BarData>,
 *     val xAxisData: AxisData = AxisData.Builder().build(),
 *     val yAxisData: AxisData = AxisData.Builder().build(),
 *     val backgroundColor: Color = Color.White,
 *     val horizontalExtraSpace: Dp = 0.dp,
 *     val barStyle: BarStyle = BarStyle(),
 *     val paddingEnd: Dp = 10.dp,
 *     val paddingTop: Dp = 0.dp,
 *     val tapPadding: Dp = 10.dp,
 *     val showYAxis: Boolean = true,
 *     val showXAxis: Boolean = true,
 *     val accessibilityConfig: AccessibilityConfig = AccessibilityConfig(),
 *     val barChartType: BarChartType = BarChartType.VERTICAL,
 *     val drawBar: (DrawScope, BarData, Offset, Float, BarChartType, BarStyle) -> Unit = { drawScope, barChartData, drawOffset, height, barChartType, barStyle ->
 *         //default implementation
 *         drawBarGraph(drawScope, barChartData, drawOffset, height, barChartType, barStyle)
 *     }
 * )
 * data class LineChartData(
 *     val linePlotData: LinePlotData,
 *     val xAxisData: AxisData = AxisData.Builder().build(),
 *     val yAxisData: AxisData = AxisData.Builder().build(),
 *     val isZoomAllowed: Boolean = true,
 *     val paddingTop: Dp = 30.dp,
 *     val bottomPadding: Dp = 10.dp,
 *     val paddingRight: Dp = 10.dp,
 *     val containerPaddingEnd: Dp = 15.dp,
 *     val backgroundColor: Color = Color.White,
 *     val gridLines: GridLines? = null,
 *     val accessibilityConfig: AccessibilityConfig = AccessibilityConfig()
 * )
 *
 * //usage
 *
Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.button5Text),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row{
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {
                    TODO()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text=stringResource(R.string.miesiac),
                    color=MaterialTheme.colorScheme.background)
            }

        }
        Spacer(modifier = Modifier.width(5.dp))

        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                TODO()
            },
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.dodaj),
                color=MaterialTheme.colorScheme.background)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                TODO()
            },
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.usun),
                color=MaterialTheme.colorScheme.background)
        }
    }
}
       **/