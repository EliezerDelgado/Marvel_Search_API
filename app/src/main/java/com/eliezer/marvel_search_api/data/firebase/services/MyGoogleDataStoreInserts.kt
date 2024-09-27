package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.usersCollection


class MyGoogleDataStoreInserts {

    fun insertCharacter(idUser : String,idCharacter : String) {
        // Create a new user with a first and last name
        val character = hashMapOf(
            "characterId" to idCharacter
        )
        // Add a new document with a generated ID
        usersCollection?.apply {
                document(idUser)
                .collection("characters")
                .document(idCharacter)
                .set(character)
        }
    }
    fun insertComic(idUser : String,idComic : String) {
        // Create a new user with a first and last name
        val comic = hashMapOf(
            "comicId" to idComic
        )
        // Add a new document with a generated ID
        usersCollection?.apply {
                document(idUser)
                .collection("comics")
                .document(idComic)
                .set(comic)
        }
    }
}