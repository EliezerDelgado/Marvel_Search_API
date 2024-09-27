package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.firestore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference


class MyGoogleDataStoreInserts {

    fun insertCharacter(idUser : String,idCharacter : String) {
        // Create a new user with a first and last name
        val character = hashMapOf(
            "characterId" to idCharacter
        )

// Add a new document with a generated ID
        val l = firestore!!.collection("characters").document(idUser).collection("character").document(idCharacter)
        l.set(character)
    }
}