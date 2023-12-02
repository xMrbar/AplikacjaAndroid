package com.example.aplikacjaandroid.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CreateAccountUIState(
    val userEmail: String = "",
    val userPassword: String = "",
    val userName: String = "",
    val userLastName: String = "",
    val communicat: String = ""
)


class CreateAccountViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LogInUIState())
    val uiState: StateFlow<LogInUIState> = _uiState.asStateFlow()

    var userName by mutableStateOf("")
        private set
    var userLastName by mutableStateOf("")
        private set

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    var repeatedPassword by mutableStateOf("")
        private set


    fun updateUserName(name:String){
        userName = name
    }
    fun updateUserLastName(lastName:String){
        userLastName = lastName}

    fun updateUserEmail(email:String){
        userEmail = email
    }

    fun updateUserPassword(password:String){
        userPassword = password
    }

    fun updateRepeatedPassword(rePassword:String){
        repeatedPassword= rePassword
    }


    fun processUserInput(){


    }



    private fun createAccount(email: String, password: String, name: String, lastName: String ){

        //TODO
    }


}