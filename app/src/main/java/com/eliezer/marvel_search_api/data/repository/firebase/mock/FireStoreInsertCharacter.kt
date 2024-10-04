package com.eliezer.marvel_search_api.data.repository.firebase.mock

import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import javax.inject.Inject


class FireStoreInsertCharacter @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    fun insertFavoriteCharacter(characterInt : Int) {
        firebaseRepository.insertFavoriteIdCharacter(characterInt)
    }
}