package com.example.aplikacjaandroid

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DataBaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private fun getFileFromDBToLocalFile(file: String)
    {

    }

    fun getAllFilesFromDBToLocalFiles()
    {

    }
}