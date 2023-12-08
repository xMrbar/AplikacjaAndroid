package com.example.aplikacjaandroid.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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


class ChangePasswordViewModel : ViewModel() {

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

    private fun updateState(){
        _uiState.update{ _uiState.value.copy(userPassword = userPassword, newPassword = newPassword, repeatedNewPassword = repeatedNewPassword ) }
    }

    private fun clearAllFields(){
        _uiState.update{ _uiState.value.copy(userPassword = "", newPassword = "", repeatedNewPassword = "" ) }
    }

    private fun updateCommunicat(communicat: String){

        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }


    // for now it probably doesn't check is new password is the same as current possword
    private fun reauthenticate(callback: (FirebaseUser) -> Unit){

        val user = auth.currentUser!!
        val userEmail = user.email.toString()

        val credentials = EmailAuthProvider.getCredential(userEmail, userPassword)

        user.reauthenticate(credentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Konto zostało pomyślnie utworzone
                    Log.d("FirebaseAuthManager", "Reauthentication success : Identity confirmed")
                    callback(user)
                } else {

                    Log.d(
                        "FirebaseAuthManager",
                        "Reauthentication failure: unable to confirm identity"
                    )
                    updateCommunicat("Błędne hasło")
                }

            }
    }

    private fun changeUserPassword(user: FirebaseUser){

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuthManager", "User password updated.")
                    updateCommunicat("Hasło zostało zmienione")
                    clearAllFields()
                }
                else{
                    Log.d("FirebaseAuthManager", "Password change failure for user: ${user.email.toString()}")
                    updateCommunicat("Wystąpił bład. Hasło nie zostało zmienione")
                }
            }
    }


    fun isNewPasswordValid(): Boolean{
        return newPassword.length >= 6
    }


    fun changePasswordHandler(){

        // empty fields
        if( userPassword.isNullOrEmpty() || newPassword.isNullOrEmpty() || repeatedNewPassword.isNullOrEmpty()){
            updateCommunicat("Puste pola")
            clearAllFields()
            return
        }

        // new password and repeated password does not match
        if( newPassword != repeatedNewPassword){
            updateCommunicat("Hasła nie są takie same")
            clearAllFields()
            return
        }


        if(isNewPasswordValid()){
            updateState()
            reauthenticate{ user -> changeUserPassword(user)}
        }
        else{
            updateCommunicat("Hasło musi mieć co najmniej 6 znaków.")
            clearAllFields()
        }

    }






}