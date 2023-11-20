package com.example.aplikacjaandroid

import android.app.LauncherActivity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.listitem.ListItem
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.selectfield.Text
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText

class StatisticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StatisticsView()
                }
            }
        }
    }
}

@Composable
@Preview
fun StatisticsView(){
    Statistics(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun Statistics(modifier : Modifier = Modifier, context: Context){

    val innerModifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {
        LabelLarge(text = stringResource(id = R.string.statystyki))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
            ButtonNarrow(
                text = stringResource(id = R.string.wydatek),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
                property1 = Property1.Variant2
            )
            ButtonNarrow(
                text = stringResource(id = R.string.przychod),
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
            )
        }

        SelectField(
            modifier =  Modifier.fillMaxWidth(),
            text = "Miesiąc",
        )


        Column(verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    BorderStroke(
                        1.dp,
                        Color(ContextCompat.getColor(LocalContext.current, R.color.outline))
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))

        ){

            UnderlinedText(text = "Grudzień 2023", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier
                .height(230.dp)
                .background(Color.Gray))
        }
        Column(verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    BorderStroke(
                        1.dp,
                        Color(ContextCompat.getColor(LocalContext.current, R.color.outline))
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))

        ){
            ListItem(itemName = "Item 1", itemValue = "value 1", color = Color.Blue)
            ListItem(itemName = "Item 2", itemValue = "value 2", color = Color.Red)
            ListItem(itemName = "Item 3", itemValue = "value 3", color = Color.Cyan)
            ListItem(itemName = "Item 4", itemValue = "value 4", color = Color.Yellow)
            ListItem(itemName = "Item 5", itemValue = "value 5", color = Color.Magenta)

        }
        Row(modifier = Modifier.fillMaxWidth()
            .background(Color(ContextCompat.getColor(LocalContext.current, R.color.primary)), shape = RoundedCornerShape(4.dp)),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

            Text(text = "Suma:")
            Text(text = "999.99zł")
        }






    }
}
