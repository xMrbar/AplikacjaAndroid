package com.example.aplikacjaandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader

class RevenuesPlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RevenuesPlanView()
                }
            }
        }
    }

    fun appendToFile(s: String, context: Context) {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, "revenuesPlan.txt")

        try {
            val fileWriter = FileWriter(file, true)
            fileWriter.append("$s\n")
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
        val file = File(externalDir, "revenuesPlan.txt")
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
        val file = File(externalDir, "revenuesPlan.txt")

        try {
            val outputStream = FileOutputStream(file)
            for(item in items) {
                outputStream.write((item.text + ";"
                        + item.date + ";"
                        + item.amount.toString() + ";"
                        + item.imageResource.toString() + ";"
                        + item.platnosc + ";"
                        + item.notatka + "\n").toByteArray())
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

@Composable
fun RevenuesPlanView() {
    RevenuesPlan(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), LocalContext.current)
}

@Composable
fun RevenuesPlan(modifier : Modifier = Modifier, context: Context) {
    val localActivity = (LocalContext.current as? Activity)
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.PlanowanieBudzetu),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {

                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text=stringResource(R.string.przychody),
                    color=MaterialTheme.colorScheme.background)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.extraLarge),
                onClick = {
                    val intentButtonPBA = Intent(context, ExpensesPlanActivity::class.java)
                    context.startActivity(intentButtonPBA)
                    localActivity?.finish()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.background)
                ) {
                Text(
                    stringResource(R.string.stanKonta),
                    color=MaterialTheme.colorScheme.tertiary)
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
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
                    text = stringResource(id = R.string.planowaneDochodyWMiesiacu),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.planowaneDochodyWMiesiacu_kwota),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            /*itemsIndexed(listItems) { index, listItem ->
                ListItemView(listItem, index)
            }*/
            items(6) {
                Test()
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                TODO()
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
            },
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.usun),
                color=MaterialTheme.colorScheme.background)
        }
    }
}


/*@Composable
fun ListItemView(listItem: LauncherActivity.ListItem, index: Int) {
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = listItem.imageResource),
                contentDescription = null
            )
            // Tekst
            Text(listItem.text, color = Color.White, modifier = Modifier.padding(start = 16.dp))
        }
    }
}
*/
