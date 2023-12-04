package com.example.aplikacjaandroid.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


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