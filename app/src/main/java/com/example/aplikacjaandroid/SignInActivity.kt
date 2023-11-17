package com.example.aplikacjaandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import com.example.aplikacjaandroid.avataricon.AvatarIcon
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.labelsmall.LabelSmall
import com.example.aplikacjaandroid.piggyicon.PiggyIcon
import com.example.aplikacjaandroid.textinput.TextInput
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignInView()
                }
            }
        }
    }
}

@Composable
@Preview
fun SignInView(){
    SignIn(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun SignIn(modifier : Modifier = Modifier, context: Context){

    val inputModifier: Modifier = Modifier.padding(8.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {

        Column(modifier = Modifier.padding(20.dp).fillMaxWidth()
            ,verticalArrangement = Arrangement.Center
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            LabelLarge(text = stringResource(id = R.string.zaloguj))
            AvatarIcon()
            Spacer(modifier = Modifier.height(40.dp))
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.adres_email),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
                )
            TextInput(modifier = inputModifier, title = stringResource(id = R.string.haslo),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show() }
                )
        }
        Spacer(modifier = Modifier.weight(1f))

        // button at the bottom
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonWide(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.zaloguj),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                    val navigate = Intent(context, MainMenuActivity::class.java)
                    context.startActivity(navigate)
                }
            )
            Spacer(modifier = Modifier
                .height(65.dp)
                .padding(8.dp))
        }
    }
}

