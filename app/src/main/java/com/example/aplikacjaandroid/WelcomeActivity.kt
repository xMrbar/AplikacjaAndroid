package com.example.aplikacjaandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.aplikacjaandroid.buttonwide.ButtonWide
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.labelsmall.LabelSmall
import com.example.aplikacjaandroid.listitem.ListItem
import com.example.aplikacjaandroid.piggyicon.PiggyIcon
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
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
    Welcome(modifier = Modifier.wrapContentHeight()
        .padding(10.dp), LocalContext.current)
}

@Composable
fun Welcome(modifier : Modifier = Modifier, context: Context){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
//        LabelLarge(text = stringResource(id = R.string.rozpocznij))
//        PiggyIcon(modifier = modifier)
//        UnderlinedText(text = "Coś", onClick = {Toast.makeText(context, "Działa", Toast.LENGTH_SHORT).show()})
//        LabelSmall(text = stringResource(id = R.string.zaloguj_lub_utworz_konto))
        ButtonWide(text = stringResource(id = R.string.zaloguj),
            onClick = {Toast.makeText(context, "Zaloguj", Toast.LENGTH_SHORT).show()}, modifier = modifier)
        ButtonWide(text = stringResource(id = R.string.utworz_konto),
            onClick = {Toast.makeText(context, "Utwórz konto", Toast.LENGTH_SHORT).show()})

//        Text(text = stringResource(R.string.zaloguj_lub_utworz_konto))
//        Button(onClick = {
//            val navigate = Intent(context, SignInActivity::class.java)
//            context.startActivity(navigate)
//
//        }) {
//            Text(stringResource(R.string.zaloguj))
//        }
//        Button(onClick = {
//            val navigate = Intent(context, CreateAccountActivity::class.java)
//            context.startActivity(navigate)
//
//
//        }) {
//            Text(stringResource(R.string.utworz_konto))
//        }
    }

}