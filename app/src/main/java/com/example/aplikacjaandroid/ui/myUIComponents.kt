package com.example.aplikacjaandroid.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal

@Composable
    fun InputText(
        title: String,
        tytul: MutableStateFlow<String>
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
                onValueChange = { enteredText = it
                    tytul.value = enteredText
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
