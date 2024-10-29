package com.eliezer.marvel_search_api.data.repository.firebase.mock

import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreDeleteComic @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    fun deleteFavoriteComic(comicInt : Int) {
        firebaseRepository.deleteFavoriteIdComic(comicInt)
    }
}