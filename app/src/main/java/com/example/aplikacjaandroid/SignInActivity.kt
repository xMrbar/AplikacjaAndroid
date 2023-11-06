package com.example.aplikacjaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun SignInView(){
    SignIn(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Preview
@Composable
fun SignIn(modifier : Modifier = Modifier){

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.zaloguj))
        Image( painter = painterResource(R.drawable.icon_avatar), contentDescription = "Avatar icon")
        Spacer(modifier = Modifier.height(16.dp))
        SimpleTextField(label = "Adres email")
        SimpleTextField(label = "Hasło")
        Button(onClick = { TODO() }) {
            Text(stringResource(R.string.zaloguj))
        }
    }
}

@Composable
fun SimpleTextField(modifier : Modifier = Modifier, label: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier){
        Text(text = label)
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            }
        )
    }

}

