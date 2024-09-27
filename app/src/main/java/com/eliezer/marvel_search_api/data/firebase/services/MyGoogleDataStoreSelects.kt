package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.usersCollection

class MyGoogleDataStoreSelects {
    fun getCharacterId(idUser : String) : ArrayList<Int>
    {
        val idCharacter = arrayListOf<Int>()
        usersCollection?.also {
            val docRef = it.document(idUser).collection("characters")
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
        usersCollection?.also {
            val docRef = it.document(idUser).collection("comics")
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