package com.example.aplikacjaandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.ui.components.CustomTextInput
import com.example.aplikacjaandroid.ui.components.InputCount
import com.example.aplikacjaandroid.ui.components.MyCalendar
import com.example.aplikacjaandroid.ui.components.MySelectBox
import com.example.aplikacjaandroid.viewmodels.AddNewOutPlanViewModel

@Composable
@Preview
fun AddNewOutPlanView(){
    AddNewOutPlanScreen(modifier = Modifier
        .fillMaxSize(),
        onRevenuesAddButtonClickedHandler = { },
        onExpensesAddButtonClickedHandler = { },
        onClickGoBack = { })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewOutPlanScreen(modifier : Modifier = Modifier,
                         addNewOutPlanViewModel: AddNewOutPlanViewModel
                                = viewModel(),
                         onRevenuesAddButtonClickedHandler: () -> Unit,
                         onExpensesAddButtonClickedHandler: () -> Unit,
                         onClickGoBack: () -> Unit
){
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {
        LabelLarge(text = stringResource(id = R.string.dodaj), modifier = Modifier.testTag("addLabel"))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
            ButtonNarrow(
                modifier = Modifier
                    .width(120.dp),
                text = stringResource(id = R.string.przychod),
                onClick = onRevenuesAddButtonClickedHandler,
                property1 = Property1.Variant2
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(120.dp),
                text = stringResource(id = R.string.stanKonta),
                onClick = {  }
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(120.dp),
                text = stringResource(id = R.string.wydatek),
                onClick = onExpensesAddButtonClickedHandler,
                property1 = Property1.Variant2
            )
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally)
        {
            CustomTextInput(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth(), "Tytuł", addNewOutPlanViewModel.tytul, onClick = {},
                onValueChanged = { addNewOutPlanViewModel.updateTytul(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MyCalendar(title="Data", text = addNewOutPlanViewModel.selectedOption1,
                expanded = addNewOutPlanViewModel.expanded, expandedChange = { addNewOutPlanViewModel.updateExpanded(it)},
                onChangeValue = {addNewOutPlanViewModel.updateData(it)})
            Spacer(modifier = Modifier.height(20.dp))
            InputCount(title="Kwota", tytul=addNewOutPlanViewModel.kwota,
                onValueChanged = { addNewOutPlanViewModel.updateKwota(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MySelectBox(addNewOutPlanViewModel.optionsType, addNewOutPlanViewModel.selectedOption2, onClick = { addNewOutPlanViewModel.updateKategoria(it) },
                expanded = addNewOutPlanViewModel.expanded1
            ) { addNewOutPlanViewModel.updateExpanded1(it) }
        }

        Spacer(modifier = Modifier.weight(1f))
        ButtonWide(
            modifier = Modifier.padding(8.dp).testTag("B"),
            text = stringResource(id = R.string.dodaj),
            onClick = {
                addNewOutPlanViewModel.appendToFile(context, onClickGoBack)
            }
        )
    }
}