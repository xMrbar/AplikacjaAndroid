package com.example.aplikacjaandroid.ui
import android.content.Context
import android.icu.number.NumberFormatter.UnitWidth
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.DataBaseManager
import com.example.aplikacjaandroid.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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
    val dataBaseManager = DataBaseManager()
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

    private fun updateCommunicat(communicat: String){

        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }

    fun isUserInputValid(): Boolean{

        //checking for blank fields
        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()){
            _uiState.update { _uiState.value.copy(communicat = "Puste pola",userPassword = "", userEmail = "")}
            return false
            }

        _uiState.update { _uiState.value.copy(userEmail = userEmail, userPassword = userPassword) }


        return true
    }

    private fun userExists(): Boolean{

       return true

    }


    fun signIn(callback: () -> Unit, context: Context){

        val email: String = _uiState.value.userEmail
        val password :String = _uiState.value.userPassword
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuthManager", "SignUp: success")
                    dataBaseManager.getAllFilesFromDBToLocalFiles(context)
                    callback()

                } else {
                    when {

                        task.exception is FirebaseAuthInvalidCredentialsException -> {
                            Log.w("FirebaseAuthManager", "SignIn: failure - Invalid password", task.exception)
                            updateCommunicat("Niepoprawny login lub hasło")
                            _uiState.update { _uiState.value.copy(userEmail = userEmail, userPassword = "") }
                        }
                        else -> {
                            Log.w("FirebaseAuthManager", "SignIn: failure", task.exception)
                            updateCommunicat("Wystąpił bład")
                        }
                    }
                }
            }
    }



}