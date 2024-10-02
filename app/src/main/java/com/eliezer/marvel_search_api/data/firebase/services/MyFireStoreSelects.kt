package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyFireStoreSelects @Inject constructor() {
    fun getCharactersId(idUser : String) : Result<ArrayList<Int>>
    {
        val idCharacter = arrayListOf<Int>()
        var ex : Exception? = null
        usersCollection?.also {
            val docRef = it.document(idUser).collection("characters")
            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        idCharacter.add(document.id.toInt())
                    }
                }
                .addOnFailureListener { exception ->
                    ex = Exception( "Error getting documents.", exception)
                }
        }
        if(ex !=null )
            return Result.failure(ex!!)
        return Result.success(idCharacter)
    }
    fun getComicsId(idUser : String) : Result<ArrayList<Int>>
    {
        val idCharacter = arrayListOf<Int>()
        var ex : Exception? = null
        usersCollection?.also {
            val docRef = it.document(idUser).collection("comics")
            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        idCharacter.add(document.id.toInt())
                    }
                }
                .addOnFailureListener { exception ->
                    ex = Exception( "Error getting documents.", exception)
                }
        }
        if(ex !=null )
            return Result.failure(ex!!)
        return Result.success(idCharacter)
    }
}