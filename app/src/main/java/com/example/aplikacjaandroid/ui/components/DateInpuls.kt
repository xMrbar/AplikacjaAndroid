package com.example.aplikacjaandroid.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.buttonnarrow.ButtonNarrow
import com.example.aplikacjaandroid.buttonnarrow.Property1
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MySelectBox(
    options: List<String>,
    text: String,
    modifier: Modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
    onClick: (String)->Unit,
    expanded: Boolean,
    expandedChange: (Boolean) -> Unit)
{
    Column {
        SelectField(
            modifier = modifier,
            text = text
        ) { expandedChange(true) }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expandedChange(false) },
            modifier = modifier
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onClick(option)
                        expandedChange(false)
                    }
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendar(title: String,
               text: String,
               modifier: Modifier = Modifier
                   .padding(4.dp)
                   .fillMaxWidth(),
                expanded: Boolean,
                expandedChange: (Boolean) -> Unit,
               onChangeValue: (DatePickerState)->Unit
) {
    val state = rememberDatePickerState()

    Box{
        TextInput(
            modifier = modifier,
            title = title,
            textContent = text,
        )
        ButtonNarrow(
            modifier = modifier
                .offset(y = 25.9.dp)
                .testTag("A"),
            onClick = {
                expandedChange(!expanded)
            },
            property1 = Property1.Variant2
        )
        if (expanded) {
            DatePickerDialog(
                onDismissRequest = { expandedChange(false) },
                confirmButton = {
                    Button(
                        onClick = {
                            onChangeValue(state)
                            expandedChange(false)
                        }
                    ) {
                        Text("OK")
                    }
                                },
                dismissButton = {
                    Button(
                        onClick = {
                            expandedChange(false)
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
}