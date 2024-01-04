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
import com.example.aplikacjaandroid.viewmodels.AddNewExpenseViewModel

@Composable
@Preview
fun AddNewExpenseView(){
    AddNewExpenseScreen(modifier = Modifier
        .fillMaxSize(),
        onRevenuesAddButtonClickedHandler = { },
        onOutPlanAddButtonClickedHandler = { })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewExpenseScreen(modifier : Modifier = Modifier,
                         addNewExpenseViewModel: AddNewExpenseViewModel
                                = viewModel(),
                         onRevenuesAddButtonClickedHandler: () -> Unit,
                         onOutPlanAddButtonClickedHandler: () -> Unit
){
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {
        LabelLarge(text = stringResource(id = R.string.dodaj))


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
                onClick = onOutPlanAddButtonClickedHandler,
                property1 = Property1.Variant2
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(120.dp),
                text = stringResource(id = R.string.wydatek),
                onClick = {

                }
            )
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally)
        {
            CustomTextInput(Modifier.padding(4.dp).fillMaxWidth(), "Tytuł", addNewExpenseViewModel.tytul, onClick = {},
                onValueChanged = { addNewExpenseViewModel.updateTytul(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MyCalendar(title="Data", text = addNewExpenseViewModel.selectedOption1,
                expanded = addNewExpenseViewModel.expanded, expandedChange = { addNewExpenseViewModel.updateExpanded(it)},
                onChangeValue = {addNewExpenseViewModel.updateData(it)})
            Spacer(modifier = Modifier.height(20.dp))
            InputCount(title="Kwota", tytul=addNewExpenseViewModel.kwota,
                onValueChanged = { addNewExpenseViewModel.updateKwota(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MySelectBox(addNewExpenseViewModel.optionsType, addNewExpenseViewModel.selectedOption2, onClick = { addNewExpenseViewModel.updateKategoria(it) },
                expanded = addNewExpenseViewModel.expanded1, expandedChange = { addNewExpenseViewModel.updateExpanded1(it)})
        }

        Spacer(modifier = Modifier.weight(1f))
        ButtonWide(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.dodaj),
            onClick = {
                addNewExpenseViewModel.appendToFile(context)
            }
        )
    }
}