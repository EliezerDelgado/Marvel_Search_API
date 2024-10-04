package com.eliezer.marvel_search_api.data.firebase.controllers

import android.content.Context
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreDelete
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreInserts
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
    private val myFireStoreSelects: MyFireStoreSelects,
    private val myFireStoreInserts: MyFireStoreInserts,
    private val myFireStoreDelete: MyFireStoreDelete
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

    fun getFavoritesIdCharacters(): Flow<Result<ArrayList<Int>>> = flow {
        emit(
            myFireStoreSelects.getCharactersId(LocalAccount.email)
        )
    }

    fun getFavoritesIdComics(): Flow<Result<ArrayList<Int>>> = flow {
        emit(
            myFireStoreSelects.getComicsId(LocalAccount.email)
        )
    }

    fun insertFavoriteIdCharacter(idCharacter: Int) = flow {
        emit(
            myFireStoreInserts.insertCharacter(
                idUser = LocalAccount.email,
               idCharacter =  idCharacter.toString()
            )
        )
    }
    fun insertFavoriteIdComic(idComic: Int) : Flow<Unit> = flow {
        emit(
            myFireStoreInserts.insertComic(
                idUser = LocalAccount.email,
                idComic =  idComic.toString()
            )
        )
    }

    fun deleteFavoriteIdCharacter(idCharacter: Int) = flow {
        emit(
            myFireStoreDelete.deleteCharacter(
                idUser = LocalAccount.email,
                idCharacter =  idCharacter.toString()
            )
        )
    }
    fun deleteFavoriteIdComic(idComic: Int) = flow {
        emit(
            myFireStoreDelete.deleteComic(
                idUser = LocalAccount.email,
                idComic =  idComic.toString()
            )
        )
    }
}