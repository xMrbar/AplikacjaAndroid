package com.example.aplikacjaandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme

class ExpensesPlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpensesPlanView()
                }
            }
        }
    }
}

@Composable
fun ExpensesPlanView() {
    ExpensesPlan(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), LocalContext.current)
}

@Composable
fun ExpensesPlan(modifier : Modifier = Modifier, context: Context) {
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
                    .height(50.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                onClick = {
                    val intentButtonPBA = Intent(context, RevenuesPlanActivity::class.java)
                    context.startActivity(intentButtonPBA)
                    localActivity?.finish()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.background)
                ) {
                Text(
                    text=stringResource(R.string.przychody),
                    color=MaterialTheme.colorScheme.tertiary)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {

                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.tertiary)) {
                Text(
                    stringResource(R.string.wydatki),
                    color=MaterialTheme.colorScheme.background)
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
                    text = stringResource(id = R.string.planowanyBudzetWMiesiacu),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.planowanyBudzetWMiesiacu_kwota),
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

@Composable
fun Test()
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.tv),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
            )
            // Tekst
            Text(
                text = "TESTOWA TESTOWA 12\nTESTOWA TESTOWA 12\nTESTOWA TESTOWA 12",
                color = MaterialTheme.colorScheme.background
            )

            Column(
                modifier = Modifier
                    .height(90.dp)
                    .width(300.dp)
                    .wrapContentSize(Alignment.BottomEnd)
            ) {
                Text(
                    text = "56000.00zł",
                    color = MaterialTheme.colorScheme.background,
                )
                Text(
                    text = "CO 3 MIESIĄCE",
                    color = MaterialTheme.colorScheme.background,
                )
            }
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
