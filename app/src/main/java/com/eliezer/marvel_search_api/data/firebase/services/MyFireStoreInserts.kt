package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MyFireStoreInserts @Inject constructor() {

    fun insertCharacter(idUser : String,idCharacter : String) {
        val character = hashMapOf(
            "characterId" to idCharacter
        )
        usersCollection?.apply {
                document(idUser)
                .collection("characters")
                .document(idCharacter)
                .set(character)
        }
    }
    fun insertComic(idUser : String,idComic : String) {
        val comic = hashMapOf(
            "comicId" to idComic
        )
        usersCollection?.apply {
                document(idUser)
                .collection("comics")
                .document(idComic)
                .set(comic)
        }
    }
}