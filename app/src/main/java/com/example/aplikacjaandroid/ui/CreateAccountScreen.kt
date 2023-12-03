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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.ui.components.CustomTextInput


@Composable
@Preview
fun CreateAccountPreview(){
    CreateAccountScreen(modifier = Modifier.fillMaxSize(),
        onCreateAccountButtonClickedHandler = {}
    )
}

@Composable
fun CreateAccountScreen(modifier : Modifier = Modifier,
                        onCreateAccountButtonClickedHandler: () -> Unit,
                        createAccountViewModel: CreateAccountViewModel = viewModel()
                        ){

    val createAccountUiState by createAccountViewModel.uiState.collectAsState()

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
            Text(
                text = createAccountUiState.communicat,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(8.dp),
                color = colorResource(R.color.outline)
            )
            CustomTextInput(modifier = Modifier.fillMaxWidth(), title = stringResource(id = R.string.imie),
                onClick = {}, onValueChanged = {createAccountViewModel.updateUserName(it)}, textContent = createAccountViewModel.userName
            )
            Spacer(Modifier.height(10.dp))
            CustomTextInput(modifier = Modifier.fillMaxWidth(), title = stringResource(id = R.string.nazwisko),
                onClick = {}, onValueChanged = {createAccountViewModel.updateUserLastName(it)}, textContent = createAccountViewModel.userLastName
            )
            Spacer(Modifier.height(10.dp))
            CustomTextInput(modifier = Modifier.fillMaxWidth(), title = stringResource(id = R.string.adres_email),
                onClick = {}, onValueChanged = {createAccountViewModel.updateUserEmail(it)}, textContent = createAccountViewModel.userEmail
            )
            Spacer(Modifier.height(10.dp))
            CustomTextInput(modifier = Modifier.fillMaxWidth(), title = stringResource(id = R.string.haslo),
                onClick = {}, onValueChanged = {createAccountViewModel.updateUserPassword(it) }, textContent = createAccountViewModel.userPassword
            )
            Spacer(Modifier.height(10.dp))
            CustomTextInput(modifier = Modifier.fillMaxWidth(), title = stringResource(id = R.string.powtorz_haslo),
                onClick = {}, onValueChanged = {createAccountViewModel.updateRepeatedPassword(it)}, textContent = createAccountViewModel.repeatedPassword
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
                onClick = {if (createAccountViewModel.isUserInputValid())
                    onCreateAccountButtonClickedHandler()
                }
            )
            Spacer(modifier = Modifier
                .height(65.dp)
                .padding(8.dp))

        }
    }
}
