package com.example.aplikacjaandroid.viewmodels
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class LogInUIState(
    val userEmail: String = "",
    val userPassword: String = "",
    val communicat: String = ""
    )

/*
 * ViewModel for SignInScreen
 */

class SignInViewModel(private val auth: FirebaseAuth = FirebaseAuth.getInstance()): ViewModel() {

    private val _uiState = MutableStateFlow(LogInUIState())
    val uiState: StateFlow<LogInUIState> = _uiState.asStateFlow()

    //TODO (hardcoded value)
    private val AUTH_TAG = "FirebaseAuth"


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

    // check if user input is correct
    // updates ui state if it is
    fun isUserInputValid(): Boolean{


        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()){

            //empty fields
            _uiState.update { _uiState.value.copy(communicat = "Puste pola",userPassword = "", userEmail = "")}
            return false
            }

        _uiState.update { _uiState.value.copy(userEmail = userEmail, userPassword = userPassword) }
        return true
    }


    /* attempts to sign user in
    * callback if invoked if attempt is successfully
    */

    fun signIn(callback: () -> Unit){

        val email: String = _uiState.value.userEmail
        val password :String = _uiState.value.userPassword

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AUTH_TAG, "User $email signed is successfully.")
                    // loading data from firestore db to local files
                    callback()

                } else {
                    when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            Log.w(AUTH_TAG, "SignIn failure.  Invalid password for user $email", task.exception)
                            updateCommunicat("Niepoprawny login lub hasło")
                            _uiState.update { _uiState.value.copy(userEmail = userEmail, userPassword = "") }
                        }

                        else -> {
                            Log.w(AUTH_TAG, "SignIn: failure. Something went wrong.", task.exception)
                            updateCommunicat("Wystąpił bład")
                        }
                    }
                }
            }
    }



}