package com.eliezer.marvel_search_api.data.firebase.controllers

import android.content.Context
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreSelects
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAuth
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseController @Inject constructor(
    private val myFirebaseAuth: MyFirebaseAuth,
    private val myFireStoreSelects: MyFireStoreSelects
) {
    fun signInExistingGoogleAccount(context: Context): Flow<Result<AuthResult>> =
        callbackFlow {
            trySend(myFirebaseAuth.googleSignInExistingAccount(context))
            awaitClose { }
        }
    fun signInAddNewGoogleAccount(context: Context): Flow<Result<AuthResult>> =
        callbackFlow {
            trySend(myFirebaseAuth.googleSignInAddNewAccount(context))
            awaitClose { }
        }
    fun getFavoritesIdCharacters() : Flow<Result<ArrayList<Int>>> = flow {
            emit(
                myFireStoreSelects.getCharactersId(LocalAccount.email)
            )
        }
    fun getFavoritesIdComics() : Flow<Result<ArrayList<Int>>> = flow {
        emit(
            myFireStoreSelects.getComicsId(LocalAccount.email)
        )
    }
    fun insertFavoriteIdCharacter(idCharacter : Int)
    {

    }
}