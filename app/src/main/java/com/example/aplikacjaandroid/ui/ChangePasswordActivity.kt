package com.example.aplikacjaandroid.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme

class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChangePasswordView()
                }
            }
        }
    }
}

@Composable
@Preview
fun ChangePasswordView(){
    ChangePassword(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun ChangePassword(modifier : Modifier = Modifier, context: Context){

    val inputModifier: Modifier = Modifier.padding(8.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        Column(modifier = Modifier.padding(20.dp).fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.zmien_haslo))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.obecne_haslo),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.nowe_haslo),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.powtorz_nowe_haslo),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonWide(
                modifier = inputModifier,
                text = stringResource(id = R.string.zatwierdz),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()

                }
            )
        }

    }
}