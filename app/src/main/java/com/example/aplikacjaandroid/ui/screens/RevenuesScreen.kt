@file:JvmName("RevenuesActivityKt")

package com.example.aplikacjaandroid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.ui.components.PieChartView
import com.example.aplikacjaandroid.viewmodels.RevenuesViewModel

@Composable
@Preview
fun RevenuesView(){
    RevenuesScreen(modifier = Modifier.fillMaxWidth(),
        onAccountBalanceButtonClickedHandler = { },
        onExpensesButtonClickedHandler = { },
        onAddNewRevenueButtonClickedHandler = { })
}

@Composable
fun RevenuesScreen(modifier : Modifier,
                 onAccountBalanceButtonClickedHandler: () -> Unit,
                 onExpensesButtonClickedHandler: () -> Unit,
                   onAddNewRevenueButtonClickedHandler: () -> Unit,
                   revenuesViewModel: RevenuesViewModel = RevenuesViewModel(LocalContext.current)
)
{
    val dochodyWTymMiesiacu by revenuesViewModel.dochodyWTymMiesiacu.collectAsState()
    val planowaneDochodyWMiesiacuKwota by revenuesViewModel.planowaneDochodyWMiesiacuKwota.collectAsState()
    val myItems by revenuesViewModel.myItems.collectAsState()
    val selectedIndex by revenuesViewModel.selectedIndex.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.przegladPrzychodow),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            ButtonNarrow(
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp),
                onClick = {

                },
                text = stringResource(R.string.przychody)
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp),
                onClick = onAccountBalanceButtonClickedHandler,
                property1 = Property1.Variant2,
                text = stringResource(R.string.stanKonta)
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp),
                onClick = onExpensesButtonClickedHandler,
                property1 = Property1.Variant2,
                text = stringResource(R.string.wydatki)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(260.dp),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            PieChartView(dochodyWTymMiesiacu, planowaneDochodyWMiesiacuKwota)
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.dochodyWplynelo),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
                Text(
                    text = dochodyWTymMiesiacu,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.dochodyZPlanowanych),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
                Text(
                    text = planowaneDochodyWMiesiacuKwota,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(myItems.size) {index ->
                ItemScroll(index = index,
                    itemD = myItems[index],
                    isSelected = index == selectedIndex,
                    onTaskClick = {
                        revenuesViewModel.onClick(index)
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
                revenuesViewModel.resetIndex()
                onAddNewRevenueButtonClickedHandler()
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                stringResource(R.string.dodaj),
                color=MaterialTheme.colorScheme.background)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            onClick = {
                revenuesViewModel.delete()
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                stringResource(R.string.usun),
                color=MaterialTheme.colorScheme.background)
        }
    }
}