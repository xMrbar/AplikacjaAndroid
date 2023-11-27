package com.example.aplikacjaandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikacjaandroid.AccountBalanceActivity
import com.example.aplikacjaandroid.HistoryAnalysisActivity
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.RevenuesPlanActivity
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import java.io.File

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenuView()
                }
            }
        }
    }
}

@Composable
fun MainMenuView() {
    val context = LocalContext.current
    CreateFile(context = context)
    MainMenu(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), context)
}

@Composable
fun MainMenu(modifier : Modifier = Modifier, context: Context) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.menu),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(200.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                val intentButton = Intent(context, AccountBalanceActivity::class.java)
                context.startActivity(intentButton)
        }) {
            Text(stringResource(R.string.button1Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                val intentButton = Intent(context, RevenuesPlanActivity::class.java)
                context.startActivity(intentButton)
        }) {
            Text(stringResource(R.string.button2Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
            TODO()
        }) {
            Text(stringResource(R.string.button3Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                val intentButton = Intent(context, HistoryAnalysisActivity::class.java)
                context.startActivity(intentButton)
            }) {
            Text(stringResource(R.string.button5Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                val intentButton = Intent(context, UserAccountActivity::class.java)
                context.startActivity(intentButton)
        }) {
            Text(stringResource(R.string.button4Text))
        }
    }
}

@Composable
fun CreateFile(context: Context)
{
    val externalDir = context.getExternalFilesDir(null)
    try {
        var file = File(externalDir, "revenues.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "expenses.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "accountBalance.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "revenuesPlan.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "expensesPlan.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
    }
    catch (e: Exception)
    {
        e.printStackTrace()
    }
}