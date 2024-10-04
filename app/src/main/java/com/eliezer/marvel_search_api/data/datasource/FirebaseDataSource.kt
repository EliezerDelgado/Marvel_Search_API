package com.eliezer.marvel_search_api.data.datasource

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {
    fun signInGoogleExistingAccount(context: Context): Flow<Result<AuthResult>>
    fun signInAddGoogleNewAccount(context: Context): Flow<Result<AuthResult>>
    fun getFavoriteIdCharacters(): Flow<Result<ArrayList<Int>>>
    fun getFavoriteIdComics(): Flow<Result<ArrayList<Int>>>
    fun insertFavoriteIdCharacter(idCharacter: Int) : Flow<Unit>
    fun insertFavoriteIdComic(idComic: Int) : Flow<Unit>
    fun deleteFavoriteIdCharacter(idCharacter: Int) : Flow<Unit>
    fun deleteFavoriteIdComic(idComic: Int) : Flow<Unit>

}