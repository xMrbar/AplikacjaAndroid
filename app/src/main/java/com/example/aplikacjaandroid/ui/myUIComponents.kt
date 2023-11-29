package com.example.aplikacjaandroid.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date

@Composable
    fun InputText(
        title: String,
        tytul: MutableStateFlow<String>,
        maxChar: Int
    ) {
        val modifier: Modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
        var enteredText by remember { mutableStateOf("") }

        Box {
            TextInput(
                modifier = modifier,
                title = title,
                textContent = ""
            )
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.1.dp)
                    .fillMaxWidth()
                    .offset(y = 0.dp)
                    .height(56.dp),
                value = enteredText,
                onValueChange = {
                    if (it.length <= maxChar) {
                        enteredText = it
                        tytul.value = enteredText
                    }
                },
                label = { Text("") },
                singleLine = true
            )
        }
    }

@Composable
fun InputCount(
    title: String,
    tytul: MutableStateFlow<BigDecimal>
) {
    val modifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
    var enteredText by remember { mutableStateOf("") }
    val pattern = remember { Regex("^\\d+(\\.\\d{0,2})?\$") }

    Box {
        TextInput(
            modifier = modifier,
            title = title,
            textContent = ""
        )
        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(4.1.dp)
                .fillMaxWidth()
                .offset(y = 0.dp)
                .height(56.dp),
            value = enteredText,
            onValueChange = {
                if (it.isEmpty()) {
                    enteredText = ""
                    tytul.value = BigDecimal("0.00")
                }
                else if(it.matches(pattern)) {
                    enteredText = it
                    tytul.value = BigDecimal(enteredText)
                }
            },
            label = { Text("") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun mySelectBox(options: List<String>,
                text: MutableStateFlow<String>)
{
    var expanded by remember { mutableStateOf(false) }

    Column {
        SelectField(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
            text = text.value,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        text.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myCalendar(title: String,
               text: MutableStateFlow<String>)
{
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    val modifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

    Box() {
        TextInput(
            modifier = modifier,
            title = title,
            textContent = text.value
        )
        ButtonNarrow(
            modifier = modifier
                .offset(y = 25.9.dp),
            onClick = {
                openDialog.value = !openDialog.value
            },
            property1 = Property1.Variant2
        )
        if (openDialog.value) {
            DatePickerDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("OK")
                    } },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("CANCEL")
                    }
                }
            ) {
                DatePicker(
                    state = state
                )
            }
        }
    }
    if(state.selectedDateMillis == null) {
        text.value = ""
    }
    else {
        val calendar = Calendar.getInstance()
        val date = Date(state.selectedDateMillis!!)
        calendar.setTime(date)
        text.value = (calendar.get(Calendar.DAY_OF_MONTH)).toString() + "." +
                (calendar.get(Calendar.MONTH) + 1).toString() + "." +
                (calendar.get(Calendar.YEAR)).toString()
    }
}
