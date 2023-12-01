package com.example.aplikacjaandroid

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FBViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel(){

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf(null)

    fun onSignup(email: String, password: String){
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){

                if(it.isSuccessful){
                    signedIn.value = true
                }
                else{

                }
                inProgress.value = false
            }

    }

    fun login(email:String, password: String){

        inProgress.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    signedIn.value = true
                }
                else{

                }
                inProgress.value = false

            }


    }


}