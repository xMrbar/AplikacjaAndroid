package com.example.aplikacjaandroid.ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class LogInUIState(
    val userEmail: String = "",
    val userPassword: String = "",
    val communicat: String = ""
    )

class SignInViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LogInUIState())
    val uiState: StateFlow<LogInUIState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateUserEmail(email:String){
        userEmail = email
    }

    fun updateUserPassword(password:String){
        userPassword = password
    }

    private fun clearFields(){
        userEmail = ""
        userPassword = ""
        _uiState.update { _uiState.value.copy(userEmail ="", userPassword = "") }
    }

    fun isUserInputValid(): Boolean{

        //checking for blank fields
        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()){
            _uiState.update { _uiState.value.copy(communicat = "Puste pola",userPassword = "", userEmail = "")}
            return false
            }

        _uiState.update { _uiState.value.copy(userEmail = userEmail, userPassword = userPassword) }

        if( ! userExists()){
            _uiState.update { _uiState.value.copy(communicat = "Użytkownik nie istnieje", userPassword = "", userEmail = "") }
            userPassword =""
            return false
        }

        return true
    }

    private fun userExists(): Boolean{

        // check if user exists using FBAuth
        return false
    }


    fun signIn(){

        //authentication logic
        //TODO

    }



}