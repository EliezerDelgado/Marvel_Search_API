package com.eliezer.marvel_search_api.data.repository.firebase.mock

import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import javax.inject.Inject


class FireStoreDeleteCharacter @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    fun deleteFavoriteCharacter(characterInt : Int) {
        firebaseRepository.deleteFavoriteIdCharacter(characterInt)
    }
}