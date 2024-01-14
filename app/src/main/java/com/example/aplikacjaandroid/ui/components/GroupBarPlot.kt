package com.example.aplikacjaandroid.ui.components
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.barCornerSize
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.barSpacing
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.barVisualMaxThreshold
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.barVisualMinThreshold
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.barWidth
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.groupBarAndLabelContainerHeight
import com.example.aplikacjaandroid.ui.components.BarChartDefaults.groupBarContainerHeight
import java.math.BigDecimal
import kotlin.math.abs


private object BarChartDefaults {
    const val barVisualMinThreshold = 0
    const val barVisualMaxThreshold = 100

    val barWidth = 15.dp
    val barSpacing = 1.dp
    val barCornerSize = 1.dp

    val groupBarContainerHeight = barVisualMaxThreshold.dp+ abs(barVisualMinThreshold).dp

    val groupBarAndLabelContainerHeight = groupBarContainerHeight+barVisualMaxThreshold.dp
}

@Composable
fun BarGraph(
    barGroups: List<BarGroup>,
    onGroupSelectionChanged: (index: Int) -> Unit = {}
) {
    val backgroundBrush = Brush.verticalGradient(
        listOf(Color.Black.copy(alpha = 0.10f), Color.Black.copy(alpha = 0.03f))
    )

    val selectedGroupIndex = remember {
        mutableStateOf(-1)
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .background(backgroundBrush)
            .padding(8.dp)
    ) {
        barGroups.forEachIndexed { index, item ->
            if (index == 0) {
                Spacer(modifier = Modifier.weight(1f))
            }
            ChartBarGroup(
                label = item.label,
                values = item.values,
                onGroupSelected = {
                    selectedGroupIndex.value = index
                    onGroupSelectionChanged(selectedGroupIndex.value)
                },
                onRemoveSelection = {
                    selectedGroupIndex.value = -1
                    onGroupSelectionChanged(selectedGroupIndex.value)
                },
                isSelected = selectedGroupIndex.value == index,
                isNothingSelected = selectedGroupIndex.value == -1
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChartBarGroup(
    modifier: Modifier = Modifier,
    label: String,
    values: List<Pair<Int, Color>>,
    onGroupSelected: () -> Unit = {},
    onRemoveSelection: () -> Unit = {},
    isSelected: Boolean,
    isNothingSelected: Boolean
) {
    Column(
        modifier = modifier
            .height(groupBarAndLabelContainerHeight)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        onGroupSelected()
                    }
                    MotionEvent.ACTION_UP -> {
                        onRemoveSelection()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        onRemoveSelection()
                    }
                }
                true
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupLabel(
            text = label,
            isHighlighted = isSelected
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.height(groupBarContainerHeight), verticalAlignment = Alignment.Bottom
        ) {
            values.forEachIndexed { index, item ->
                val (realPercentage, color) = item
                val yOffset: Int
                val applyFadingEffect = realPercentage < barVisualMinThreshold
                val percentage = realPercentage.coerceIn(barVisualMinThreshold + 1, barVisualMaxThreshold - 1)

                yOffset = if (percentage >= 0) {
                    abs(barVisualMinThreshold)
                } else if (percentage in barVisualMinThreshold..-1) {
                    abs(barVisualMinThreshold) + percentage
                } else {
                    0
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    ChartBar(
                        percentage = percentage,
                        brush = if (applyFadingEffect) {
                            Brush.verticalGradient(listOf(color, color.copy(alpha = 0f)))
                        } else {
                            Brush.verticalGradient(listOf(color, color))
                        },
                        isHighlighted = isSelected || isNothingSelected
                    )
                    Spacer(modifier = Modifier.height(yOffset.dp))
                }
                if (index in 0 until values.size - 1) {
                    Spacer(modifier = Modifier.width(barSpacing))
                }
            }
        }
    }
}


@Composable
private fun GroupLabel(
    text: String,
    color: Color = Color.Black,
    highlightColor: Color =  Color.Black,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    isHighlighted: Boolean = false
) {
    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = text,
        color = if (isHighlighted) highlightColor else color,
        style = textStyle
    )
}

@Composable
fun ChartBar(
    modifier: Modifier = Modifier,
    percentage: Int,
    brush: Brush,
    isHighlighted: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(barCornerSize))
            .height(abs(percentage).dp)
            .width(barWidth)
            .background(brush)
            .background(color = if (!isHighlighted) Color.Black.copy(alpha = 0.5f) else Color.Transparent)
    )
}

data class BarGroup(
    val label: String,
    val values: List<Pair<Int, Color>>
)
data class BarInfo(
    val label: String,
    val values: List<BigDecimal>
)