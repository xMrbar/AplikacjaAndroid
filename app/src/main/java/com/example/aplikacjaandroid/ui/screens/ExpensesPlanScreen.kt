package com.example.aplikacjaandroid.ui.screens

import android.annotation.SuppressLint
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
import com.example.aplikacjaandroid.viewmodels.ExpensesPlanViewModel


@Composable
@Preview
fun ExpensesPlanView(){
    ExpensesPlanScreen(modifier = Modifier.fillMaxWidth(),
        onAddExpensesPlanAddButtonClieckedHandler = { },
        onRevenuesPlanButtonClickedHandler = { })
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ExpensesPlanScreen(modifier : Modifier,
                       onRevenuesPlanButtonClickedHandler: () -> Unit,
                       onAddExpensesPlanAddButtonClieckedHandler: () -> Unit,
                       expensesPlanViewModel: ExpensesPlanViewModel = ExpensesPlanViewModel(LocalContext.current)
)
{
    val planowaneWydatkiWMiesiacuKwota by expensesPlanViewModel.planowaneWydatkiWMiesiacuKwota.collectAsState()
    val myItems by expensesPlanViewModel.myItems.collectAsState()
    val selectedIndex by expensesPlanViewModel.selectedIndex.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = stringResource(R.string.PlanowanieBudzetu),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            ButtonNarrow(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = onRevenuesPlanButtonClickedHandler,
                property1 = Property1.Variant2,
                text = stringResource(R.string.przychody)
            )
            Spacer(modifier = Modifier.width(40.dp))
            ButtonNarrow(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {

                },
                text = stringResource(R.string.wydatki))
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
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = planowaneWydatkiWMiesiacuKwota,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 17.sp,
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
                        expensesPlanViewModel.onClick(index)
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
                expensesPlanViewModel.resetIndex()
                onAddExpensesPlanAddButtonClieckedHandler()
            },
            shape = RoundedCornerShape(4.dp),
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
            onClick = { expensesPlanViewModel.delete() },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.usun),
                color=MaterialTheme.colorScheme.background)
        }
    }
}