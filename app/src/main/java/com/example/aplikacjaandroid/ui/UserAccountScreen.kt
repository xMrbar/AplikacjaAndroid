package com.example.aplikacjaandroid.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinputwithicon.TextInputWithIcon


@Composable
@Preview
fun UserAccountPreview(){
    UserAccountScreen(modifier = Modifier.fillMaxSize(),
        onChangePasswordButtonClickedHandler = {}
        )
}

@Composable
fun UserAccountScreen(modifier : Modifier = Modifier,
                      onChangePasswordButtonClickedHandler: () -> Unit

                      ){

    val inputModifier: Modifier = Modifier.padding(8.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        Column(modifier = Modifier.padding(20.dp).fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.twoje_konto))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            TextInputWithIcon(modifier = inputModifier, title = stringResource(id = R.string.adres_email),
                onClick = {}
            )
            TextInputWithIcon(modifier = inputModifier, title = stringResource(id = R.string.imie),
                onClick = {}
            )
            TextInputWithIcon(modifier = inputModifier, title = stringResource(id = R.string.nazwisko),
                onClick = {}
            )

            Spacer(modifier = Modifier.height(30.dp))
            ButtonWide(
                modifier = inputModifier,
                text = stringResource(id = R.string.zmien_haslo),
                onClick = onChangePasswordButtonClickedHandler
            )

        }

    }
}