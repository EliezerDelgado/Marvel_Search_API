package com.eliezer.marvel_search_api.domain.repository

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun signInGoogleExistingAccount(context: Context) : Flow<Result<AuthResult>>
    fun signInAddGoogleNewAccount(context: Context) : Flow<Result<AuthResult>>
    fun getFavoriteIdCharacters(): Flow<Result<ArrayList<Int>>>
    fun getFavoriteIdComics(): Flow<Result<ArrayList<Int>>>
    fun insertFavoriteIdCharacter(idCharacter: Int)
    fun insertFavoriteIdComic(idComic: Int)
    fun deleteFavoriteIdCharacter(idCharacter: Int)
    fun deleteFavoriteIdComic(idComic: Int)
    fun analytics()
}