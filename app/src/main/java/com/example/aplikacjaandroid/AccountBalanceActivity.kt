package com.example.aplikacjaandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader

class AccountBalanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AccountList()
                }
            }
        }
    }
}

@Composable
fun AccountList()
{
    val context = LocalContext.current
    val localActivity = (LocalContext.current as? Activity)
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)

    val counter = Counter(LocalContext.current)
    var stanKonta by remember { mutableStateOf(counter.countActualBallance().toString() + "zł") }
    var stanKontaNaKoniecMiesiaca by remember { mutableStateOf(counter.countEstaminatedBallance().toString() + "zł") }

    val fileManager = FileManager("accountBalance.txt")
    var myItems by remember { mutableStateOf(fileManager.readItemsFromFile(context)) }
    var newItem by remember { mutableStateOf("LAMBO;03.10.2023;100000;AUTO") }
    //var newItem by remember { mutableStateOf("") }

    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text=stringResource(R.string.przegladKonta),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            Button(
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                onClick = {
                    val intentButtonPBA = Intent(context, RevenuesActivity::class.java)
                    context.startActivity(intentButtonPBA)
                    localActivity?.finish()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text=stringResource(R.string.przychody),
                    color=MaterialTheme.colorScheme.tertiary)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp),
                onClick = {

                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text=stringResource(R.string.stanKonta),
                    color=MaterialTheme.colorScheme.background)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                onClick = {
                    val intentButtonPBA = Intent(context, ExpensesActivity::class.java)
                    context.startActivity(intentButtonPBA)
                    localActivity?.finish()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    stringResource(R.string.wydatki),
                    color=MaterialTheme.colorScheme.tertiary)
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .size(210.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.accountBalance),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 18.sp
                )
                Text(
                    text = stanKonta,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.accountBalancePlanEnd),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 18.sp
                )
                Text(
                    text = stanKontaNaKoniecMiesiaca,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(myItems.size) {index ->
                ItemScroll(index = index,
                    itemD = myItems[index],
                    isSelected = index == selectedIndex,
                    onTaskClick = {
                        selectedIndex = if (index == selectedIndex) -1 else index
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                selectedIndex = -1
                fileManager.appendToFile(newItem, context)
                myItems = fileManager.readItemsFromFile(context)
            },
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.dodaj),
                color=MaterialTheme.colorScheme.background)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                fileManager.deleteItemFromFile(context, selectedIndex)
                selectedIndex = -1
                myItems = fileManager.readItemsFromFile(context)
            },
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.usun),
                color=MaterialTheme.colorScheme.background)
        }
    }
}

@Composable
fun ItemScroll(
    index: Int,
    itemD: ItemData,
    isSelected: Boolean,
    onTaskClick: () -> Unit,
) {
    val backgroundColor = if (index % 2 == 0) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.secondary
    }

    val textColor = if (index % 2 == 0) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.background
    }

    val image = if (itemD.imageResource == 0) {
        R.drawable.car
    } else if (itemD.imageResource == 1) {
        R.drawable.electricity
    } else if (itemD.imageResource == 2) {
        R.drawable.green
    } else if (itemD.imageResource == 3) {
        R.drawable.plane
    } else if (itemD.imageResource == 4) {
        R.drawable.png
    } else if (itemD.imageResource == 5) {
        R.drawable.shirt
    } else {
        R.drawable.tv
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(if (isSelected) MaterialTheme.colorScheme.error else backgroundColor)
            .clickable { onTaskClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
            )
            // Tekst
            Text(
                text = itemD.text,
                color = textColor
            )

            Column(
                modifier = Modifier
                    .height(90.dp)
                    .width(300.dp)
                    .wrapContentSize(Alignment.CenterEnd)
            ) {
                Text(
                    text = itemD.amount.toString() + "zł",
                    color = textColor,
                )
                Text(
                    text = itemD.date,
                    color = textColor,
                )
            }
        }
    }
}