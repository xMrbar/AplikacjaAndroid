package com.example.aplikacjaandroid

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.labellarge.LabelLarge
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import com.example.aplikacjaandroid.underlinedtext.UnderlinedText

class HistoryAnalysisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacjaAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryAnalysisView()
                }
            }
        }
    }
}

@Composable
@Preview
fun HistoryAnalysisView() {
    HistoryAnalysis(modifier = Modifier
        .fillMaxSize(), LocalContext.current)
}

@Composable
fun HistoryAnalysis(modifier : Modifier = Modifier, context: Context) {
    //val localActivity = (LocalContext.current as? Activity)

    val innerModifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp), verticalArrangement = Arrangement.SpaceAround

    ) {
        LabelLarge(text = stringResource(id = R.string.button5Text))


        SelectField(
            modifier = Modifier.fillMaxWidth(),
            text = "Miesiąc",
        )


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
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
                .padding(4.dp)

        ) {

            UnderlinedText(text = "Grudzień 2023", modifier = Modifier.fillMaxWidth())
            Spacer(
                modifier = Modifier
                    .height(0.dp)
                    .background(Color.Gray)
            )
            /*Image(
                painter = painterResource(R.drawable.month_plot),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
            )*/
        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    Color(ContextCompat.getColor(LocalContext.current, R.color.primary)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            com.example.aplikacjaandroid.selectfield.Text(text = "5988.19zł")
            com.example.aplikacjaandroid.selectfield.Text(text = "WYDATKI")
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    Color(ContextCompat.getColor(LocalContext.current, R.color.primary)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            com.example.aplikacjaandroid.selectfield.Text(text = "7860.00zł")
            com.example.aplikacjaandroid.selectfield.Text(text = "PRZYCHODY")

        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            ButtonNarrow(
                text = "Miesiąc",
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
                property1 = Property1.Variant2
            )
            ButtonNarrow(
                text = "Rok",
                onClick = {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }
}

/**
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.button5Text),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row{
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {
                    TODO()
                },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text=stringResource(R.string.miesiac),
                    color=MaterialTheme.colorScheme.background)
            }

        }
        Spacer(modifier = Modifier.width(5.dp))

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
       **/