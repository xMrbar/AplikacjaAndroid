package com.example.aplikacjaandroid.ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class SignInUiState(
    val userEmail: String = "",
    val userPassword: String = ""
    )

class SignInViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateUserEmail(email:String){
        _uiState.value = _uiState.value.copy(userEmail = email)
    }

    fun updateUserPassword(password:String){
        _uiState.value = _uiState.value.copy(userPassword = password)
    }



}