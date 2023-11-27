package com.example.aplikacjaandroid.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinput.TextInput


@Composable
@Preview
fun CreateAccountPreview(){
    CreateAccountScreen(modifier = Modifier
        .fillMaxSize())
}

@Composable
fun CreateAccountScreen(modifier : Modifier = Modifier){

    val inputModifier: Modifier = Modifier.padding(8.dp).fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.rejestracja))
            Spacer(modifier = Modifier.height(40.dp))

            TextInput(modifier = inputModifier, title = stringResource(id = R.string.imie),
                onClick = {}
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.nazwisko),
                onClick = {}
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.adres_email),
                onClick = {}
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.haslo),
                onClick = {}
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.powtorz_haslo),
                onClick = {}
            )


        }


        Spacer(modifier = Modifier.weight(1f))

        // button at the bottom
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonWide(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.utworz_konto),
                onClick = {}
            )
            Spacer(modifier = Modifier
                .height(65.dp)
                .padding(8.dp))

        }
    }
}
