package com.example.aplikacjaandroid.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
    // communicat that will be shown to user when something is wrong
    val communicat: String = "",
)

/*
 * ViewModel for ChangePasswordScreen
 */

class ChangePasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordUIState())
    val uiState: StateFlow<ChangePasswordUIState> = _uiState.asStateFlow()
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    //TODO (hardcoded value)
    private val TAG = "ChangePasswordViewModel"
    private val AUTH_TAG = "FirebaseAuth"

    // user inputs, not yet saved in uiState
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

    // update state with userPassword, newPassword and repeatedNewPassword
    private fun updateState(){
        _uiState.update{ _uiState.value.copy(userPassword = userPassword, newPassword = newPassword, repeatedNewPassword = repeatedNewPassword ) }
    }

    private fun clearAllFields(){
        userPassword = ""
        newPassword = ""
        repeatedNewPassword = ""
        updateState()
    }

    private fun updateCommunicat(communicat: String){
        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }


    // function checks if entered current password is correct
    // is reauthentication is successful callback function is called

    private fun reauthenticate(callback: (FirebaseUser) -> Unit){

        val user = auth.currentUser!!
        val userEmail = user.email.toString()

        val credentials = EmailAuthProvider.getCredential(userEmail, userPassword)

        user.reauthenticate(credentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d(AUTH_TAG, "Reauthentication success : Identity of ${user.email} is confirmed")
                    callback(user)
                } else {

                    Log.d(AUTH_TAG, "Reauthentication failure: unable to confirm ${user.email}'s identity"
                    )

                    //TODO (hardcoded value)
                    updateCommunicat("Błędne hasło")
                }
            }
    }

    //changes current user password using FirebaseAuth
    private fun changeUserPassword(user: FirebaseUser){

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User's (${user.email}) password changed successfully" )
                    updateCommunicat("Hasło zostało zmienione")
                    clearAllFields()
                }
                else{
                    Log.d(TAG, "Password change failure for user: ${user.email}")

                    //TODO (hardcoded value)
                    updateCommunicat("Wystąpił bład. Hasło nie zostało zmienione")
                }
            }
    }

    // checks if password meets requirements
    fun isNewPasswordValid(): Boolean{
        return newPassword.length >= 6
    }


    /* handles password change
     * check if ui state is valid for password change
     * if it is it handles password change with callbacks
     */

    fun changePasswordHandler(){

        // empty fields
        if( userPassword.isNullOrEmpty() || newPassword.isNullOrEmpty() || repeatedNewPassword.isNullOrEmpty()){
            //TODO (hardcoded value)
            updateCommunicat("Puste pola")
            clearAllFields()
            return
        }

        // new password and repeatePassword does not match
        if( newPassword != repeatedNewPassword){
            //TODO (hardcoded value)
            updateCommunicat("Hasła nie są takie same")
            clearAllFields()
            return
        }

        if(isNewPasswordValid()){

            updateState()
            reauthenticate{ user -> changeUserPassword(user)}
        }
        else{
            //TODO (hardcoded value)
            updateCommunicat("Hasło musi mieć co najmniej 6 znaków.")
            clearAllFields()
        }

    }






}