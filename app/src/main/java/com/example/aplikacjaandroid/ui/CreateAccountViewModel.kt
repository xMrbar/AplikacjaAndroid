package com.example.aplikacjaandroid.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CreateAccountUIState(
    val userEmail: String = "",
    val userPassword: String = "",
    val userName: String = "",
    val userLastName: String = "",
    val communicat: String = ""
)


class CreateAccountViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUIState())
    val uiState: StateFlow<CreateAccountUIState> = _uiState.asStateFlow()

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


    fun isUserInputValid(): Boolean{


        //empty fields
        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty() || repeatedPassword.isNullOrEmpty()
            || userName.isNullOrEmpty() || userLastName.isNullOrEmpty()){

            clearAllFields()
            updateCommunicat("Puste pola")
            return false
        }

        //user already  exists
        if(userAlreadyExists()){
            clearLoginFields()
            updateCommunicat("Taki użytkownik już istnieje")
            return false
        }

        // passowrds does not match
        if( repeatedPassword != userPassword){
            clearPasswordFields()
            updateCommunicat("Hasła nie są takie same")
            return false
        }

        // OK

        updateCommunicat("")
        updateState()
        return true;
    }


    private fun userAlreadyExists(): Boolean{
        return false
    }

    private fun clearLoginFields(){

        userEmail =""
        userPassword =""
        repeatedPassword =""

        updateState()
    }


    private fun clearPasswordFields(){
        userPassword =""
        repeatedPassword =""

        updateState()

    }

    private fun clearAllFields(){

        userName = ""
        userLastName = ""
        userEmail = ""
        userPassword = ""
        repeatedPassword = ""

        updateState()

    }

    private fun updateState(){

        _uiState.update { _uiState.value.copy(userName = userName, userLastName = userLastName, userEmail = userEmail, userPassword = userPassword, )}

    }



    private fun updateCommunicat(communicat: String){

        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }



    private fun createAccount(email: String, password: String, name: String, lastName: String ){

        //TODO
    }


}