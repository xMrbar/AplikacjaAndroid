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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinput.TextInput

@Composable
@Preview
fun SignInPreview(){
    SignInScreen(modifier = Modifier.fillMaxSize(),
        onSignInButtonClickedHandler = {}
        )
}

@Composable
fun SignInScreen(modifier : Modifier = Modifier,
                 onSignInButtonClickedHandler: () -> Unit,
                 signInViewModel: SignInViewModel = viewModel()
                 ) {

    val signInUiState by signInViewModel.uiState.collectAsState()

    val inputModifier: Modifier = Modifier.padding(8.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LabelLarge(text = stringResource(id = R.string.zaloguj))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(value = signInUiState.userEmail,
                singleLine = true,
                onValueChange = { signInViewModel.updateUserEmail(it) }
            )
            OutlinedTextField(value = signInUiState.userPassword,
                singleLine = true,
                onValueChange = { signInViewModel.updateUserPassword(it) }
            )


        }
        Spacer(modifier = Modifier.weight(1f))

        // button at the bottom
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonWide(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.zaloguj),
                onClick = onSignInButtonClickedHandler
            )
            Spacer(
                modifier = Modifier
                    .height(65.dp)
                    .padding(8.dp)
            )
        }
    }
}

