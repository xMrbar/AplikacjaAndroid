package com.example.aplikacjaandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
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
                    AccountList(appender = { string, context ->
                        appendToFile(string, context)
                    },
                        reader = { context ->
                            readItemsFromFile(context)
                        }
                    )
                }
            }
        }
    }
    fun appendToFile(s: String, context: Context) {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, "accountBalance.txt")

        try {
            val fileWriter = FileWriter(file, true)
            // Dopisz dane do pliku
            fileWriter.append("$s\n")
            // Zamknij strumień
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteItemFromFile(context: Context, position: Int)
    {
        val items = readItemsFromFile(context).toMutableList()
        if (position in items.indices)
        {
            items.removeAt(position)
            writeItemsToFile(context, items)
        }
    }

    fun readItemsFromFile(context: Context): List<ItemData>
    {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, "accountBalance.txt")
        val inputStream = FileInputStream(file)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        var index: Int = 0
        var items = mutableListOf<ItemData>()
        while (line != null) {
            val parts = line.split(";")
            val title = parts[0]
            val date = parts[1]
            val money = parts[2]
            val type = parts[3]
            val platnosc = parts[4]
            val notatka = parts[5]
            val typeInt = if (type.contains("CAR", ignoreCase = true)) {
                0
            } else if(type.contains("ELECTRICITY", ignoreCase = true)) {
                1
            } else if(type.contains("INCOME", ignoreCase = true)) {
                2
            } else if(type.contains("TRAVEL", ignoreCase = true)) {
                3
            } else if(type.contains("HOME", ignoreCase = true)) {
                4
            } else if(type.contains("CLOTHES", ignoreCase = true)) {
                5
            } else {
                6
            }
            items.add(ItemData(typeInt, title, money.toFloat(), date, platnosc, notatka))
            line = reader.readLine()
            index += 1
        }
        inputStream.close()
        return items
    }

    fun writeItemsToFile(context: Context, items: List<ItemData>)
    {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, "accountBalance.txt")

        try {
            val outputStream = FileOutputStream(file)
            for(item in items) {
                outputStream.write((item.text + "; "
                        + item.date + "; "
                        + item.amount.toString() + "; "
                        + item.imageResource.toString() + "; "
                        + item.platnosc + "; "
                        + item.notatka + "\n").toByteArray())
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

@Composable
fun AccountList(
    appender: (String, Context) -> Unit,
    reader: (Context) -> List<ItemData>
    )
{
    val context = LocalContext.current
    val localActivity = (LocalContext.current as? Activity)
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)

    var myItems by remember { mutableStateOf(reader(context)) }
    var newItem by remember { mutableStateOf("LAMBO; 03.10.2023; 100000; AUTO; tak; nie") }
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text="PRZEGLĄD KONTA",
            //text=stringResource(R.string.przegladKonta),
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
                    val intentButtonPBA = Intent(context, ExpensesActivity::class.java)
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
                    val intentButtonPBA = Intent(context, RevenuesActivity::class.java)
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
                    text = "Aktualnie na\nkoncie posiadasz:",
                    //stringResource(R.string.accountBalance),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 18.sp
                )
                Text(
                    text="0.00zł",
                    //text = stringResource(id = R.string.accountBalance_kwota),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text="planowany stan konta\nna koniec miesiąca",
                    //text = stringResource(id = R.string.accountBalancePlanEnd),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 18.sp
                )
                Text(
                    text="0.00zł",
                    //text = stringResource(id = R.string.accountBalancePlanEnd_kwota),
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
            //var index = 0
            //for(itemD in myItems) {
                //index += 1
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
                appender(newItem, context)
                myItems = reader(context)
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
                TODO()
                //readBalanceActivity(context)
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
                    .wrapContentSize(Alignment.BottomEnd)
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

data class ItemData(
    val imageResource: Int,
    val text: String,
    val amount: Float,
    val date: String,
    val platnosc: String,
    val notatka: String
)
