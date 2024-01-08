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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.aplikacjaandroid.viewmodels.HistoryAnalysisViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.aplikacjaandroid.ui.components.mySelectBox
import com.example.aplikacjaandroid.ui.components.BarchartWithSolidBars
import com.example.aplikacjaandroid.ui.components.MySelectBox
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText
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

    LaunchedEffect(historyAnalysisViewModel) {
        historyAnalysisViewModel.initializeAnalysis(context)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp), verticalArrangement = Arrangement.SpaceAround

    ) {
        MySelectBox(options = listOf("2021", "2022", "2023"), "Rok", Modifier.fillMaxWidth(), onClick = { historyAnalysisViewModel.updateYear(it)}, expanded = historyAnalysisViewModel.expanded
        ) { historyAnalysisViewModel.updateExpanded(it) }


        if(historyAnalysisUIState.isMonthMode) {
            mySelectBox(
                options = listOf(
                    "Styczeń",
                    "Luty",
                    "Marzec",
                    "Kwiecień",
                    "Maj",
                    "Czerwiec",
                    "Lipiec",
                    "Sierpień",
                    "Wrzesień",
                    "Październik",
                    "Listopad",
                    "Grudzień"
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

            UnderlinedText(text = historyAnalysisUIState.chosenYear.toString() + " " + historyAnalysisUIState.chosenMonth.toString(), modifier = Modifier.fillMaxWidth())
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
            Text( text = "WYDATKI", color = textColor)
            Text(text = historyAnalysisUIState.expenses.toString(), color = textColor)
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
            //androidx.compose.material3.Text(text = "7860.00zł", color = Color.Black)
           // androidx.compose.material3.Text(text ="PRZYCHODY", color = Color.Black)
            Text( text = "PRZYCHODY", color = textColor)
            Text(text = historyAnalysisUIState.revenues.toString(), color = textColor)

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
