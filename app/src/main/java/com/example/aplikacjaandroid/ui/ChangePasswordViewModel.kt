package com.example.aplikacjaandroid.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class ChangePasswordUIState(
    val userPassword: String = "",
    val newPassword: String = "",
    val repeatedNewPassword: String = "",
    val communicat: String = "",
)


class ChangePasswordViewModel {

    private val _uiState = MutableStateFlow(ChangePasswordUIState())
    val uiState: StateFlow<ChangePasswordUIState> = _uiState.asStateFlow()
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    var userPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var repeatedNewPassword by mutableStateOf("")
        private set

    fun updateUserPassword(pasword:String){
        userPassword = pasword
    }

    fun updateNewPassword (password: String){
        newPassword = password
    }

    fun updateRepeatedNewPassword (password: String){
        repeatedNewPassword = password
    }

    fun updateState(){
        _uiState.update{ _uiState.value.copy(userPassword = userPassword, newPassword = newPassword, repeatedNewPassword = repeatedNewPassword ) }
    }

    private fun updateCommunicat(communicat: String){

        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }


    // for now it probably doesn't check is new password is the same as current possword
    fun reauthenticate(callback: () -> Unit){

        val user = auth.currentUser!!
        val userEmail = user.email.toString()

        val credentials = EmailAuthProvider.getCredential(userEmail, userPassword)

        user.reauthenticate(credentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Konto zostało pomyślnie utworzone
                    Log.d("FirebaseAuthManager", "Reauthentication success : Identity confirmed")
                    callback()
                }


                else {

                Log.d("FirebaseAuthManager", "Reauthentication failure: unable to confirm identity")
                updateCommunicat("Wrong password")
                }

            }

    }

}