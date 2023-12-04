package com.example.aplikacjaandroid.ui
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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

    val auth = FirebaseAuth.getInstance()
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

       return true

    }


    fun signIn(){

        val email: String = _uiState.value.userEmail
        val password :String = _uiState.value.userPassword
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuthManager", "SignUp: success")

                } else {
                    Log.w("FirebaseAuthManager", "SignUp: failure", task.exception)
                }
            }
    }



}