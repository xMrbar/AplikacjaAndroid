package com.example.aplikacjaandroid.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
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

class UserAccountViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UserAccountUIState())
    val uiState: StateFlow<UserAccountUIState> = _uiState.asStateFlow()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

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

    fun updateUserEmail(email:String){
        userEmail = email
    }

    private fun updateState(){

        _uiState.update { _uiState.value.copy(userName = userName, userLastName = userLastName, userEmail = userEmail, )}
    }

    private fun updateCommunicat(communicat: String){
        _uiState.update { _uiState.value.copy(communicat = communicat) }
    }


    fun getUserDataFromDB(){

        val user = auth.currentUser!!

        userEmail = user.email.toString()
        val userId = user.uid

        val docRef = db.collection("users").document(userId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //doc exists
                    Log.d("FirestoreData", "DocumentSnapshot data: ${document.data}")
                    val userData = document.data

                    userName = userData!!["name"].toString()
                    userLastName = userData!!["lastname"].toString()
                    updateState()

                } else {
                    //doc does not exist
                    Log.d("FirestoreData", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreData", "get failed with ", exception)
            }

    }


    @SuppressLint("SuspiciousIndentation")
    fun updateUserNameDB(){

        updateState()

        val user = auth.currentUser!!

        userEmail = user.email.toString()
        val userId = user.uid

        val userDataDocRef = db.collection("users").document(userId)

            userDataDocRef.update("name", _uiState.value.userName)
            .addOnSuccessListener {
                Log.d("FirestoreUpdate", "DocumentSnapshot successfully updated!")

                updateState()
                updateCommunicat("Name changed succesfully.")
            }

            .addOnFailureListener {
                    e -> Log.w("FirebaseUpdate", "Error updating document", e)
                    updateCommunicat("Unable to change name.")
            }



    }


    @SuppressLint("SuspiciousIndentation")
    fun updateUserLastameDB(){

        updateState()

        val user = auth.currentUser!!

        userEmail = user.email.toString()
        val userId = user.uid

        val userDataDocRef = db.collection("users").document(userId)

        userDataDocRef.update("lastname", _uiState.value.userLastName)
            .addOnSuccessListener {
                Log.d("FirestoreUpdate", "DocumentSnapshot successfully updated!")
                updateState()
                updateCommunicat("Last name changed succesfully.")
            }

            .addOnFailureListener {

                    e -> Log.w("FirebaseUpdate", "Error updating document", e)
                    updateCommunicat("Unable to change last name.")
            }


    }









}



