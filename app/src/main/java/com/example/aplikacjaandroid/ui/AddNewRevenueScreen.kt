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
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput

@Composable
@Preview
fun AddNewRevenueView(){
    AddNewRevenueScreen(modifier = Modifier
        .fillMaxSize(),
        onExpensesAddButtonClickedHandler = { },
        onOutPlanAddButtonClickedHandler = { })
}

@Composable
fun AddNewRevenueScreen(modifier : Modifier = Modifier,
                         addNewRevenueViewModel: AddNewRevenueViewModel
                                = AddNewRevenueViewModel(LocalContext.current),
                        onExpensesAddButtonClickedHandler: () -> Unit,
                        onOutPlanAddButtonClickedHandler: () -> Unit
){
    val selectCzestoscPlatnosci by addNewRevenueViewModel.selectedOption1.collectAsState()
    val kategoria by addNewRevenueViewModel.selectedOption2.collectAsState()
    val tytul by addNewRevenueViewModel.tytul.collectAsState()
    val kwota by addNewRevenueViewModel.kwota.collectAsState()
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
                onClick = {

                }
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
            addNewRevenueViewModel.textGet("Tytuł")
            Spacer(modifier = Modifier.height(30.dp))
            addNewRevenueViewModel.dateSelect()
            Spacer(modifier = Modifier.height(20.dp))
            addNewRevenueViewModel.amountGet(title = "Kwota")
            Spacer(modifier = Modifier.height(30.dp))
            addNewRevenueViewModel.categorySelect()
        }

        Spacer(modifier = Modifier.weight(1f))
        ButtonWide(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.dodaj),
            onClick = {
                //Toast.makeText(context, "SPRADŹ WPROWADZONE PARAMETRY", Toast.LENGTH_LONG).show()
                //Log.d("T1", tytul + ";" + selectCzestoscPlatnosci + ";" + kwota + ";" + kategoria)
                addNewRevenueViewModel.appendToFile(tytul, selectCzestoscPlatnosci, kwota, kategoria)
            }
        )
    }
}