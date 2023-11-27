package com.example.aplikacjaandroid.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikacjaandroid.R
import java.io.File



@Composable
@Preview
fun MainMenuView() {
    val context = LocalContext.current
    CreateFile(context = context)
    MainMenuScreen(modifier = Modifier.fillMaxSize(),
        onAccountBalanceButtonClickedHandler = {},
        onBudgetPlanButtonClickedHandler = {},
        onStatisticsButtonClickedHandler = {},
        onHistoryAnalysisButtonClickedHandler = {},
        onUserAccountButtonClickedHandler = {},
        onLogOutButtonClickedHandler = {}
        )
}

@Composable
fun MainMenuScreen(modifier : Modifier = Modifier,
                   onAccountBalanceButtonClickedHandler: () ->  Unit,
                   onBudgetPlanButtonClickedHandler: () -> Unit,
                   onStatisticsButtonClickedHandler: ()-> Unit,
                   onHistoryAnalysisButtonClickedHandler: () -> Unit,
                   onUserAccountButtonClickedHandler: () -> Unit,
                   onLogOutButtonClickedHandler: () -> Unit
                   ) {
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
            onClick = onAccountBalanceButtonClickedHandler
        ) {
            Text(stringResource(R.string.button1Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = onBudgetPlanButtonClickedHandler
        ) {
            Text(stringResource(R.string.button2Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = onStatisticsButtonClickedHandler

        ) {
            Text(stringResource(R.string.button3Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = onHistoryAnalysisButtonClickedHandler
        ) {
            Text(stringResource(R.string.button5Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = onUserAccountButtonClickedHandler
        ) {
            Text(stringResource(R.string.button4Text))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = onLogOutButtonClickedHandler
        ) {
            Text(stringResource(R.string.wyloguj))
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