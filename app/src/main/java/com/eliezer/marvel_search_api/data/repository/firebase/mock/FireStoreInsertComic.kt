package com.eliezer.marvel_search_api.data.repository.firebase.mock

import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FireStoreInsertComic @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    fun insertFavoriteComic(comicId : Int) {
        firebaseRepository.insertFavoriteIdComic(comicId)
    }
}