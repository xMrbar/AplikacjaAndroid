package com.example.aplikacjaandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.material3.Text as Text1


@Composable
@Preview
fun UserAccountPreview(){
    UserAccountScreen(
        modifier = Modifier.fillMaxSize(),
        onChangePasswordButtonClickedHandler = {},
        onDeleteAccountButtonClickedHandler = {}
        )
}

@Composable
fun UserAccountScreen(
    modifier: Modifier = Modifier,
    onChangePasswordButtonClickedHandler: () -> Unit,
    onDeleteAccountButtonClickedHandler: () -> Unit,
    userAccountViewModel: UserAccountViewModel = viewModel()
                      ){

    val inputModifier: Modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()

    val userAccountUiState by userAccountViewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        // get data form server on initial launch
        LaunchedEffect(userAccountViewModel) {
            userAccountViewModel.getUserDataFromDB()
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.twoje_konto))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            Text1(
                text = userAccountUiState.communicat,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                color = colorResource(R.color.outline)
            )
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
            Spacer(modifier = Modifier.height(15.dp))
            ButtonWide(
                modifier = inputModifier,
                text = stringResource(id = R.string.usun_konto),
                onClick = {

                    showDialog = true
                    }

            )

        }

    }

    if(showDialog){

        confirmDeleteDialog( onConfirmation = {
            userAccountViewModel.deleteUserHandler(context, onDeleteAccountButtonClickedHandler)
            showDialog = false
        },
            onDismiss = {showDialog = false})
    }

}


@Composable
fun confirmDeleteDialog(onConfirmation: () -> Unit, onDismiss: () -> Unit){

    AlertDialog(
        title = {
            Text1(text = "Usuń konto")
        },
        text = {
            Text1(text = "Czy na pewno chcesz to zrobić?")
        },
        onDismissRequest = {
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation

            ) {
                Text1("Usuń konto")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text1("Anuluj")
            }
        }
    )

    
}