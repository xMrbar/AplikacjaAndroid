package com.example.aplikacjaandroid.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal


@Composable
fun CustomTextInput(modifier: Modifier = Modifier,
                     title: String,
                     textContent: String,
                     onValueChanged: (String) -> Unit,
                     onClick: ()->Unit
                     ){

    OutlinedTextField(
        value = textContent,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(text = title) },
        isError = false,
    )
}

@Composable
fun CustomEditTextInput(modifier: Modifier = Modifier,
                    title: String,
                    textContent: String,
                    onValueChanged: (String) -> Unit,
                    onClick: ()->Unit,
                        onKeyboardDone: () -> Unit
){

    OutlinedTextField(
        value = textContent,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(text = title) },
        isError = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = androidx.compose.ui.text.input.ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun InputCount(
    title: String,
    tytul: BigDecimal,
    onValueChanged: (String) -> Unit
) {
    var kwota by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .padding(4.1.dp)
            .fillMaxWidth()
            .offset(y = 0.dp),
        value = kwota,
        onValueChange = {
            if (it.isEmpty()) {
                kwota = "0.00"
            }
            else if(it.matches(Regex("^\\d+(\\.\\d{0,2})?\$")))
            {
                kwota = it
            }
            onValueChanged(kwota)
        },
        label = { Text(title) },
        singleLine = true,
        isError = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}