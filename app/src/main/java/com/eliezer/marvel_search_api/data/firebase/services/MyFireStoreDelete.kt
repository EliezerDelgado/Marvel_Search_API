package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyFireStoreDelete @Inject constructor() {

    fun deleteCharacter(idUser : String,idCharacter : String) {
        usersCollection?.apply {
            document(idUser)
                .collection("characters")
                .document(idCharacter)
                .delete()
        }
    }
    fun deleteComic(idUser : String,idComic : String) {
        usersCollection?.apply {
            document(idUser)
                .collection("comics")
                .document(idComic)
                .delete()
        }
    }
}