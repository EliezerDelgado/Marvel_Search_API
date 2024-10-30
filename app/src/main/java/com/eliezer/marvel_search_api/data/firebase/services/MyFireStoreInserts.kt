package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import com.eliezer.marvel_search_api.data.firebase.structure.FireStoreUsersStructure
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MyFireStoreInserts @Inject constructor() {


    fun insertCharacter(idUser : String,idCharacter : String) {
        val character = hashMapOf(

            FireStoreUsersStructure.CHARACTERS_CHARACTER_ID to idCharacter
        )
        usersCollection?.apply {
                document(idUser)
                .collection(FireStoreUsersStructure.TABLE_CHARACTERS)
                .document(idCharacter)
                .set(character)
        }
    }
    fun insertComic(idUser : String,idComic : String) {
        val comic = hashMapOf(
            FireStoreUsersStructure.COMICS_COMIC_ID to idComic
        )
        usersCollection?.apply {
                document(idUser)
                .collection(FireStoreUsersStructure.TABLE_COMICS)
                .document(idComic)
                .set(comic)
        }
    }
}