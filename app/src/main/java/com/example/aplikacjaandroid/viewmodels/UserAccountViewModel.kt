package com.example.aplikacjaandroid.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserAccountUIState(
    val userEmail: String = "",
    val userName: String = "",
    val userLastName: String = "",
    val communicat: String = "",
)
/*
 * ViewModel for UserAccountScreen
 */
class UserAccountViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UserAccountUIState())
    val uiState: StateFlow<UserAccountUIState> = _uiState.asStateFlow()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private val FIRESTORE_TAG = "Firestore"
    private val AUTH_TAG = "FirebaseAuth"

    var userName by mutableStateOf("")
        private set
    var userLastName by mutableStateOf("")
        private set

    var userEmail by mutableStateOf("")
        private set

    fun updateUserName(name:String){
        userName = name
    }
    fun updateUserLastName(lastName:String){
        userLastName = lastName}

    private fun updateState(){

        _uiState.update { _uiState.value.copy(userName = userName, userLastName = userLastName, userEmail = userEmail, )}
    }

    private fun updateCommunicat(communicat: String){
        _uiState.update { _uiState.value.copy(communicat = communicat) }
    }


    // attempts to get user personal data from Firestore DB
    fun getUserDataFromDB(){

        val user = auth.currentUser!!
        userEmail = user.email.toString()
        val userId = user.uid

        // document reference for document containing user personal data
        val docRef = db.collection("users").document(userId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //doc exists
                    Log.d(FIRESTORE_TAG, "Document obtained: ${document.data}")
                    val userData = document.data

                    // updating ui state with data from document
                    userName = userData!!["name"].toString()
                    userLastName = userData!!["lastname"].toString()
                    updateState()

                } else {
                    //doc does not exist
                    Log.d(FIRESTORE_TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(FIRESTORE_TAG, "Unable to obtain document.", exception)
            }

    }


    // updates username in firestore databse
    @SuppressLint("SuspiciousIndentation")
    fun updateUserNameDB(){

        updateState()

        val user = auth.currentUser!!

        userEmail = user.email.toString()
        val userId = user.uid

        // document reference for document containing user personal data
        val userDataDocRef = db.collection("users").document(userId)

            userDataDocRef.update("name", _uiState.value.userName)
            .addOnSuccessListener {
                Log.d(FIRESTORE_TAG, "User's name successfully updated!")

                updateState()
                //TODO (hardcoded value)
                updateCommunicat("Zmieniono imię")
            }

            .addOnFailureListener { e ->
                Log.w(FIRESTORE_TAG, "Error updating document", e)
                //TODO (hardcoded value)
                updateCommunicat("Nie udało się zmieniń imienia")
            }



    }

    // updates user's lastname in firestore database
    @SuppressLint("SuspiciousIndentation")
    fun updateUserLastameDB(){

        updateState()

        val user = auth.currentUser!!

        userEmail = user.email.toString()
        val userId = user.uid

        // document reference for document containing user personal data
        val userDataDocRef = db.collection("users").document(userId)

        userDataDocRef.update("lastname", _uiState.value.userLastName)
            .addOnSuccessListener {
                Log.d(FIRESTORE_TAG, "User's last name successfully updated!")
                updateState()

                //TODO (hardcoded value)
                updateCommunicat("Zmieniono nazwisko")
            }

            .addOnFailureListener { e ->
                Log.w(FIRESTORE_TAG, "Error updating document", e)
                //TODO (hardcoded value)
                updateCommunicat("Unable to change last name.")
            }

    }


    // attempts to delete user
    fun deleteUserHandler(context: Context, callback: () -> Unit){

            val user = auth.currentUser!!

        // setting user's collection status to DELETED
        setUserCollectionStatusToDeleted(user.uid)

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(AUTH_TAG, "User account deleted: ${user.email.toString()}.")



                        // showing toast that user was deleted successfully
                        Toast.makeText(context, "Usunięto użytkownika: ${user.email.toString()}", Toast.LENGTH_SHORT).show()
                        callback()
                    }
                }
    }

    // app is unable to delete collection for database
    // so it sets user's status to "Deleted" to mark collection ad deleted too

    private fun setUserCollectionStatusToDeleted(userID: String){

        val userDataDocRef = db.collection("users").document(userID)

        userDataDocRef.update("status", "Deleted")
            .addOnSuccessListener {
                Log.d(FIRESTORE_TAG, "User ${userID}: Collection status set to DELETED ")
            }

            .addOnFailureListener {
                    e -> Log.w(FIRESTORE_TAG, "Error changing user $userID collection status to DELETED", e)
            }


    }









}



