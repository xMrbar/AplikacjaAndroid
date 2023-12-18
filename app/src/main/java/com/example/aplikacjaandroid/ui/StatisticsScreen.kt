package com.example.aplikacjaandroid.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.listitem.ListItem
import com.example.aplikacjaandroid.models.StatisticsItem
import com.example.aplikacjaandroid.models.TimeIntervalsLength
import com.example.aplikacjaandroid.selectfield.SelectField
import androidx.compose.material3.Text
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText



@Composable
@Preview
fun StatisticsPreview(){
    StatisticsScreen(modifier = Modifier
        .fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(modifier : Modifier = Modifier,
                     statisticsViewModel: StatisticsViewModel = viewModel()
                     ){

    val statisticsUIState by statisticsViewModel.uiState.collectAsState()


    var expandedTimeIntervalsLengthDropdown by remember { mutableStateOf(false) }
    var expandedTimeIntervalsDropdown by remember { mutableStateOf(false) }
    val textColor = Color(ContextCompat.getColor(LocalContext.current, R.color.backgroud))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp), verticalArrangement = Arrangement.SpaceAround

    ) {
        LabelLarge(text = stringResource(id = R.string.statystyki))

        Box(modifier = Modifier.fillMaxWidth()) {
            SelectField(
                text = statisticsUIState.timeIntervalLength.name,
                onClick = { expandedTimeIntervalsLengthDropdown = true}
            )
            DropdownMenu(
                expanded = expandedTimeIntervalsLengthDropdown,
                onDismissRequest = { expandedTimeIntervalsLengthDropdown = false },
            ) {
                TimeIntervalsLength.values().forEach { selected ->
                    DropdownMenuItem(
                        text = { Text(text = selected.name) },
                        onClick = {
                             statisticsViewModel.updateTimeIntervalLength(selected)
                             expandedTimeIntervalsLengthDropdown= false
                        }
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.SpaceEvenly,
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

        ){

            Box(modifier = Modifier.fillMaxWidth()) {
                SelectField(
                    text = statisticsUIState.timeIntervalLabel,
                    onClick = { expandedTimeIntervalsDropdown = true}
                )
                DropdownMenu(
                    expanded = expandedTimeIntervalsDropdown && statisticsUIState.isTimeIntervalDropdownExpandable,
                    onDismissRequest = { expandedTimeIntervalsDropdown = false },
                ) {
                    statisticsUIState.timeIntervalsList.forEach { selected ->
                        DropdownMenuItem(
                            text = { Text(text = selected) },
                            onClick = {
                                expandedTimeIntervalsDropdown= false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier
                .height(230.dp)
                .background(Color.Gray))
        }
        Box(
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
                .height(200.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize() // Occupy the entire size of the Box
            ) {
                items(statisticsUIState.list.size) { i ->
                    ListItem(
                        itemValue = statisticsUIState.list[i].value,
                        itemName = statisticsUIState.list[i].label,
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.outline))
                    )
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(ContextCompat.getColor(LocalContext.current, R.color.primary)),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){


            Text( text = "Suma:", color = textColor)
            Text(text = "999.99zł", color = textColor)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
            ButtonNarrow(
                text = stringResource(id = R.string.wydatek),
                onClick = {},
                property1 = Property1.Variant2
            )
            ButtonNarrow(
                text = stringResource(id = R.string.przychod),
                onClick = {},
            )
        }






    }
}
