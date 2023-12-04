package com.example.aplikacjaandroid.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.ui.components.CustomTextInput
import com.example.aplikacjaandroid.ui.components.InputCount
import com.example.aplikacjaandroid.ui.components.MySelectBox

@Composable
@Preview
fun AddNewRevenuePlanView(){
    AddNewRevenuePlanScreen(modifier = Modifier
        .fillMaxSize(),
        onExpenseAddButtonClickedHandler = { })
}

@Composable
fun AddNewRevenuePlanScreen(modifier : Modifier = Modifier,
                         addNewRevenuePlanViewModel: AddNewRevenuePlanViewModel
                                = viewModel(),
                         onExpenseAddButtonClickedHandler: () -> Unit
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
                text = stringResource(id = R.string.wydatek),
                onClick = onExpenseAddButtonClickedHandler,
                property1 = Property1.Variant2
            )
            ButtonNarrow(
                text = stringResource(id = R.string.przychod),
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
            CustomTextInput(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth(), "Tytuł", addNewRevenuePlanViewModel.tytul, onClick = {},
                onValueChanged = { addNewRevenuePlanViewModel.updateTytul(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MySelectBox(addNewRevenuePlanViewModel.optionsDate, addNewRevenuePlanViewModel.selectedOption1, onClick = { addNewRevenuePlanViewModel.updateData(it) },
                expanded = addNewRevenuePlanViewModel.expanded, expandedChange = { addNewRevenuePlanViewModel.updateExpanded(it)})
            Spacer(modifier = Modifier.height(20.dp))
            InputCount(title="Kwota", tytul=addNewRevenuePlanViewModel.kwota,
                onValueChanged = { addNewRevenuePlanViewModel.updateKwota(it) })
            Spacer(modifier = Modifier.height(30.dp))
            MySelectBox(addNewRevenuePlanViewModel.optionsType, addNewRevenuePlanViewModel.selectedOption2, onClick = { addNewRevenuePlanViewModel.updateKategoria(it) },
                expanded = addNewRevenuePlanViewModel.expanded1, expandedChange = { addNewRevenuePlanViewModel.updateExpanded1(it)})
        }

        Spacer(modifier = Modifier.weight(1f))
        ButtonWide(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.dodaj),
            onClick = {
                addNewRevenuePlanViewModel.appendToFile(context)
            }
        )
    }
}