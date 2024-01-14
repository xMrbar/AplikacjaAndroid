package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CreateAccountUIState(
    val userEmail: String = "",
    val userPassword: String = "",
    val userName: String = "",
    val userLastName: String = "",
    // communicat that will be shown to user when something is wrong
    val communicat: String = ""
)

/*
 * ViewModel for CreateAccountScreen
 */

class CreateAccountViewModel(private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
                             private val db: FirebaseFirestore = Firebase.firestore): ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUIState())
    val uiState: StateFlow<CreateAccountUIState> = _uiState.asStateFlow()

    //TODO (hardcoded value)
    private val TAG = "CreateAccountViewModel"
    private val AUTH_TAG = "FirebaseAuth"

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


    // check if user input is correct
    // updates ui state if it is
    fun validateUserInput(): Boolean{

        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty() || repeatedPassword.isNullOrEmpty()
            || userName.isNullOrEmpty() || userLastName.isNullOrEmpty()){

            //empty fields
            clearAllFields()
            updateCommunicat("Puste pola")
            return false
        }

        if( repeatedPassword != userPassword){

            // passowrds does not match
            clearPasswordFields()
            updateCommunicat("Hasła nie są takie same")
            return false
        }

        if( userPassword.length < 6){

            //passwor is to short
            clearPasswordFields()
            updateCommunicat("Hasła musi mieć co najemiej 6 znakow")
            return false
        }

        // input is OK
        updateCommunicat("")
        updateState()
        return true;
    }

    private fun updateUiUserAlreadyExists(){
        clearLoginFields()
        updateCommunicat("Taki użytkownik już istnieje")
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

        _uiState.update { _uiState.value.copy(
            userName = userName,
            userLastName = userLastName,
            userEmail = userEmail,
            userPassword = userPassword, )}

    }

    private fun updateCommunicat(communicat: String){

        _uiState.update { _uiState.value.copy(communicat = communicat)}
    }


    // creates new user account using FirebaseAuth
    fun createAccount(context: Context, onSuccessCallback: () -> Unit){

        val email: String = _uiState.value.userEmail
        val password :String = _uiState.value.userPassword

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d(AUTH_TAG, "Account created for user: $email")

                    // getting userId for creating user's collection
                    val userId: String = auth.currentUser!!.uid
                    // adding user data to database
                    addUserData(userId)
                    // creating collections for expenses and revenues
                    onSuccessCallback()

                } else {
                    when (task.exception) {
                        is FirebaseAuthUserCollisionException -> {
                            Log.d(AUTH_TAG, "Create account: Failure. User $email already exists!")
                            updateUiUserAlreadyExists()
                        }
                    }
                }
            }
    }

    // adding user collection and document with user's personal data
     private fun addUserData(userId: String){

        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val creationDate = LocalDate.now().format(dateFormat)

        val userData = hashMapOf<String,String>(

            "name" to _uiState.value.userName,
            "lastname" to _uiState.value.userLastName,
            "status" to "Active",
            "accountCreationDate" to creationDate
        )
        db.collection("users").document(userId).set(userData).addOnSuccessListener { documentReference ->
            Log.d(TAG,"Document added for id: $userId")
        }
            .addOnFailureListener { e ->
                Log.d(TAG,"Unable to add document for id: $userId ")
            }

    }

}