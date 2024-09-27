package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.firestore

class MyGoogleDataStoreSelects {
    fun getCharacterId(idUser : String) : ArrayList<Int>
    {
        val idCharacter = arrayListOf<Int>()
        firestore?.also {
            val docRef = it.collection("users").document(idUser).collection("characters")
            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        idCharacter.add(document.id.toInt())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
        return idCharacter
    }
    fun getComicId(idUser : String) : ArrayList<Int>
    {
        val idCharacter = arrayListOf<Int>()
        firestore?.also {
            val docRef = it.collection("users").document(idUser).collection("comics")
            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        idCharacter.add(document.id.toInt())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
        return idCharacter
    }
}