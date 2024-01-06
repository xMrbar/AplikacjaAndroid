package com.example.aplikacjaandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.example.aplikacjaandroid.viewmodels.ExpensesViewModel

@Composable
@Preview
fun ExpensesView(){
    ExpensesScreen(modifier = Modifier.fillMaxWidth(),
        onAccountBalanceButtonClickedHandler = { },
        onRevenuesButtonClickedHandler = { },
        onAddNewExpenseButtonClickedHandler = { })
}

@Composable
fun ExpensesScreen(modifier : Modifier,
                   onAccountBalanceButtonClickedHandler: () -> Unit,
                   onRevenuesButtonClickedHandler: () -> Unit,
                   onAddNewExpenseButtonClickedHandler: () -> Unit,
                   expensesViewModel: ExpensesViewModel = ExpensesViewModel(LocalContext.current)
)
{
    val wydatkiWTymMiesiacu by expensesViewModel.wydatkiWTymMiesiacu.collectAsState()
    val planowaneWydatkiWMiesiacuKwota by expensesViewModel.planowaneWydatkiWMiesiacu.collectAsState()
    val myItems by expensesViewModel.myItems.collectAsState()
    val selectedIndex by expensesViewModel.selectedIndex.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.przegladWydatkow),
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
                onClick = onRevenuesButtonClickedHandler,
                property1 = Property1.Variant2,
                text = stringResource(R.string.przychody)
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp),
                onClick = onAccountBalanceButtonClickedHandler,
                property1 = Property1.Variant2,
                text=stringResource(R.string.stanKonta)
            )
            Spacer(modifier = Modifier.width(5.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp),
                onClick = {

                },
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
            PieChartView(wydatkiWTymMiesiacu, planowaneWydatkiWMiesiacuKwota)
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.remainingMoneyForMonth),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
                Text(
                    text = wydatkiWTymMiesiacu,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.remainingMoneyForMonthAll),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
                Text(
                    text = planowaneWydatkiWMiesiacuKwota,
                    color = MaterialTheme.colorScheme.primary,
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
            items(myItems.size) {index ->
                ItemScroll(index = index,
                    itemD = myItems[index],
                    isSelected = index == selectedIndex,
                    onTaskClick = {
                        expensesViewModel.onClick(index)
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
                expensesViewModel.resetIndex()
                onAddNewExpenseButtonClickedHandler()
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
                expensesViewModel.delete()
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