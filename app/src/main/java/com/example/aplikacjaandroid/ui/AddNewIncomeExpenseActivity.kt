package com.example.aplikacjaandroid.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.aplikacjaandroid.textfield.TextField
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme

class AddNewIncomeExpenseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddNewExpenseView()
                }
            }
        }
    }
}

@Composable
@Preview
fun AddNewExpenseView(){
    AddExpense(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun AddExpense(modifier : Modifier = Modifier, context: Context){

    val inputModifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

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
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                }, property1 = Property1.Variant2
            )
            ButtonNarrow(text = stringResource(id = R.string.przychod),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                })
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {


            TextInput(modifier = inputModifier, title = stringResource(id = R.string.imie),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.nazwisko),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.adres_email),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            SelectField(modifier =  inputModifier, text = stringResource(id = R.string.kateogria))
            SelectField(modifier =  inputModifier, text = stringResource(id = R.string.sposob_platnosci))
            TextField( modifier = inputModifier, textContent = stringResource(id = R.string.notatka))


        }

        Spacer(modifier = Modifier.weight(1f))
        ButtonWide(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.dodaj),
            onClick = {
                Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
            }
        )


    }
}