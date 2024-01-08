package com.example.aplikacjaandroid.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Log
import com.example.aplikacjaandroid.data.DataBaseManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val dataBaseManager = DataBaseManager()

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


    fun validateUserInput(): Boolean{


        //empty fields
        if(userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty() || repeatedPassword.isNullOrEmpty()
            || userName.isNullOrEmpty() || userLastName.isNullOrEmpty()){

            clearAllFields()
            updateCommunicat("Puste pola")
            return false
        }

        // passowrds does not match
        if( repeatedPassword != userPassword){
            clearPasswordFields()
            updateCommunicat("Hasła nie są takie same")
            return false
        }

        if( userPassword.length < 6){
            clearPasswordFields()
            updateCommunicat("Hasła musi mieć co najemiej 6 znakow")
            return false
        }



        // OK
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



    fun createAccount(context: Context, onSuccessCallback: () -> Unit){

        val email: String = _uiState.value.userEmail
        val password :String = _uiState.value.userPassword
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Konto zostało pomyślnie utworzone
                    Log.d("FirebaseAuthManager", "Tworzenie konta: sukces")
                    val userId: String = auth.currentUser!!.uid
                    addUserData(userId)
                    dataBaseManager.getAllFilesFromDBToLocalFiles(context)
                    onSuccessCallback()

                } else {
                    when (task.exception) {
                        is FirebaseAuthUserCollisionException -> {
                            Log.d("FirebaseAuthManager", "Konto już istnieje")
                            updateUiUserAlreadyExists()
                        }
                    }
                }
            }
    }

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
            Log.d("Firestore","DocumentSnapshot added with ID:")
        }
            .addOnFailureListener { e ->
                Log.w("Error adding document", e)
            }

    }

}