package com.example.aplikacjaandroid.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.ui.components.CustomEditTextInput
import com.example.aplikacjaandroid.ui.components.CustomPasswordInput
import com.example.aplikacjaandroid.viewmodels.ChangePasswordViewModel


@Composable
@Preview
fun ChangePasswordPreview(){
    ChangePasswordScreen(modifier = Modifier.fillMaxSize(),
        )
}


@Composable
fun ChangePasswordScreen(modifier : Modifier = Modifier,
                         changePasswordViewModel: ChangePasswordViewModel = viewModel()
                         ){

    val inputModifier: Modifier = Modifier.padding(8.dp).fillMaxWidth()

    val changePasswordUIState = changePasswordViewModel.uiState.collectAsState()

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

            LabelLarge(text = stringResource(id = R.string.zmien_haslo))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = changePasswordUIState.value.communicat,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                color = colorResource(R.color.outline)
            )
            Spacer(modifier = Modifier.height(40.dp))

            CustomPasswordInput(modifier = inputModifier, title = stringResource(id = R.string.haslo),
                textContent = changePasswordViewModel.userPassword, onValueChanged = {changePasswordViewModel.updateUserPassword(it)}
            )
            CustomPasswordInput(modifier = inputModifier, title = stringResource(id = R.string.nowe_haslo),
                textContent = changePasswordViewModel.newPassword, onValueChanged = {changePasswordViewModel.updateNewPassword(it)}
            )
            CustomPasswordInput(modifier = inputModifier, title = stringResource(id = R.string.powtorz_haslo),

                textContent = changePasswordViewModel.repeatedNewPassword, onValueChanged = {changePasswordViewModel.updateRepeatedNewPassword(it)}
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonWide(
                modifier = inputModifier,
                text = stringResource(id = R.string.zatwierdz),
                onClick = {changePasswordViewModel.changePasswordHandler()}
            )
        }

    }
}