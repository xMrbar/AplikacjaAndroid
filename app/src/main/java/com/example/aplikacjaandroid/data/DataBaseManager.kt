package com.example.aplikacjaandroid.data

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.model.ItemData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DataBaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private fun getFileFromDBToLocalFile(file: String, context: Context, uid: String)
    {
        val docRef = db.collection("users").document(uid).collection(file)
        val fileManager = FileManager("$file.txt")
        val list = mutableListOf<String>()
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val name = document.id
                    val data = document.data["wpis"].toString()
                    val newItem = "$name;$data"
                    list.add(newItem)
                }
                fileManager.writeStringItemsToFile(context, list)
                Log.d("File loaded succesfully from DB", "Loaded $file")
            }
            .addOnFailureListener { e ->
                Log.w("Error loading document", e)
            }
    }

    fun logOut()
    {
        auth.signOut()
    }

    fun getAllFilesFromDBToLocalFiles(context: Context)
    {
        val list = listOf("revenues", "expenses", "accountBalance", "revenuesPlan", "expensesPlan")
        val user = auth.currentUser!!
        val userId = user.uid

        for(element in list) {
            getFileFromDBToLocalFile(element, context, userId)
        }
    }

    fun addItemToDataBase(collection: String, wpis: String)
    {
        val splitted = wpis.split(";")
        val id = splitted[0]
        val czystyWpis = splitted[1] + ";" + splitted[2] + ";" + splitted[3] + ";" + splitted[4]
        val user = auth.currentUser!!
        val data = hashMapOf(
            "wpis" to czystyWpis
        )
        db.collection("users").document(user.uid).collection(collection)
            .document(id).set(data).addOnSuccessListener { _ ->
                Log.d("Firestore - add item","Added document $wpis")
            }
            .addOnFailureListener { e ->
                Log.w("Error adding item", e)
            }
    }

    fun removeItemFromDataBase(collection: String, item: ItemData) {
        val user = auth.currentUser!!
        val id = item.id.toString()

        db.collection("users").document(user.uid)
            .collection(collection).document(id).delete().addOnSuccessListener { _ ->
                Log.d("Deleted document from DB", "Deleted from $user.uid and $collection document $id")
            }
            .addOnFailureListener { e ->
                Log.w("Error deleting item", e)
            }
    }
}