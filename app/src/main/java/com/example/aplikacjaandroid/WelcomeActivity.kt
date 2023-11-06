package com.example.aplikacjaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeView()
                }
            }
        }
    }
}

@Composable
fun WelcomeView(){
    Welcome(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Preview
@Composable
fun Welcome(modifier : Modifier = Modifier){



    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Image( painter = painterResource(R.drawable.piggy_icon), contentDescription = "Piggybank icon")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.rozpocznij))
        Text(text = stringResource(R.string.zaloguj_lub_utworz_konto), )
        Button(onClick = { TODO() }) {
            Text(stringResource(R.string.zaloguj))
        }
        Button(onClick = { TODO() }) {
            Text(stringResource(R.string.utworz_konto))
        }
    }

}