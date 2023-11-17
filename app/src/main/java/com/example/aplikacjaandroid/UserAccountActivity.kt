package com.example.aplikacjaandroid

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.textinputwithicon.TextInputWithIcon
import com.example.aplikacjaandroid.ui.theme.ui.theme.AplikacjaAndroidTheme

class UserAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserAccountView()
                }
            }
        }
    }
}

@Composable
@Preview
fun UserAccountView(){
    UserAccount(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun UserAccount(modifier : Modifier = Modifier, context: Context){

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
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInputWithIcon(modifier = inputModifier, title = stringResource(id = R.string.imie),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )
            TextInputWithIcon(modifier = inputModifier, title = stringResource(id = R.string.nazwisko),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
            )

            Spacer(modifier = Modifier.height(30.dp))
            ButtonWide(
                modifier = inputModifier,
                text = stringResource(id = R.string.zmien_haslo),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                    val navigate = Intent(context, ChangePasswordActivity::class.java)
                    context.startActivity(navigate)
                }
            )

        }

    }
}