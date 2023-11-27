package com.example.aplikacjaandroid.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.aplikacjaandroid.labelsmall.LabelSmall
import com.example.aplikacjaandroid.piggyicon.PiggyIcon

@Composable
@Preview
fun WelcomeView(){
    WelcomeScreen(modifier = Modifier.fillMaxWidth(),
        onSignInButtonClickedHandler = {},
        onCreateAccountButtonClickedHandler = {})
}


@Composable
fun WelcomeScreen(modifier : Modifier = Modifier,
                  onSignInButtonClickedHandler: () -> Unit,
                  onCreateAccountButtonClickedHandler: () -> Unit,
                  ){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {

        Column(modifier = Modifier.padding(30.dp),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            PiggyIcon()
            Spacer(modifier = Modifier.height(40.dp))
            LabelLarge(text = stringResource(id = R.string.rozpocznij))
            LabelSmall(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.zaloguj_lub_utworz_konto))
        }
        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonWide(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.zaloguj),
                onClick = onSignInButtonClickedHandler
            )
            ButtonWide(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.utworz_konto),
                onClick = onCreateAccountButtonClickedHandler
            )
        }
    }
}

