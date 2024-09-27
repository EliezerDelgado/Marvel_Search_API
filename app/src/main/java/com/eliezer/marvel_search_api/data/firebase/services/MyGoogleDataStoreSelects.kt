package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.firestore

class MyGoogleDataStoreSelects {
    fun getCharacterId(idUser : String) : ArrayList<Int>
    {
        val idCharacter = arrayListOf<Int>()
        val docRef = firestore!!.collection("characters").document(idUser).collection("character")
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    idCharacter.add(document.id.toInt())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return idCharacter
    }
}