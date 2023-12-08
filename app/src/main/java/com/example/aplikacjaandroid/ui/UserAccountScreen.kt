package com.example.aplikacjaandroid.ui

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.labelmedium.LabelMedium
import com.example.aplikacjaandroid.labelsmall.LabelSmall
import com.example.aplikacjaandroid.textinputwithicon.TextInputWithIcon
import com.example.aplikacjaandroid.ui.components.CustomEditTextInput
import com.example.aplikacjaandroid.ui.components.CustomOutlinedText


@Composable
@Preview
fun UserAccountPreview(){
    UserAccountScreen(
        modifier = Modifier.fillMaxSize(),
        onChangePasswordButtonClickedHandler = {}
        )
}

@Composable
fun UserAccountScreen(
    modifier: Modifier = Modifier,
    onChangePasswordButtonClickedHandler: () -> Unit,
    userAccountViewModel: UserAccountViewModel = viewModel()
                      ){

    val inputModifier: Modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()

    val userAccountUiState by userAccountViewModel.uiState.collectAsState()
    //userAccountViewModel.getUserDataFromDB()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.twoje_konto))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = userAccountUiState.communicat,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                color = colorResource(R.color.outline)
            )
//            CustomEditTextInput(modifier = inputModifier, title = stringResource(id = R.string.adres_email),
//                onClick = {}, onKeyboardDone = { },
//                textContent = userAccountViewModel.userEmail, onValueChanged = {}
//            )
            Spacer(Modifier.height(10.dp))
            CustomOutlinedText(text = userAccountUiState.userEmail)
            Spacer(Modifier.height(10.dp))
            CustomEditTextInput(modifier = inputModifier, title = stringResource(id = R.string.imie),
                onClick = {}, onKeyboardDone = {userAccountViewModel.updateUserNameDB()},
                textContent = userAccountViewModel.userName, onValueChanged = {userAccountViewModel.updateUserName(it)}
            )
            Spacer(Modifier.height(10.dp))
            CustomEditTextInput(modifier = inputModifier, title = stringResource(id = R.string.nazwisko),
                onClick = {}, onKeyboardDone = {userAccountViewModel.updateUserLastameDB()},
                textContent = userAccountViewModel.userLastName, onValueChanged = {userAccountViewModel.updateUserLastName(it)}
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