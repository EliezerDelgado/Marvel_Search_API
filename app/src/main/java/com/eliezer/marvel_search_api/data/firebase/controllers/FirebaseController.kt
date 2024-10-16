package com.eliezer.marvel_search_api.data.firebase.controllers

import android.content.Context
import androidx.credentials.Credential
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreDelete
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreInserts
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreSelects
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAuth
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
    fun signInGoogleAccountBefore(context: Context): Flow<Result<AuthResult>> =
        callbackFlow {
            trySend(myFirebaseAuth.googleSignInAccountBefore(context))
            awaitClose { }
        }
    fun signInWithCredentialsGoogleAccount(credential: Credential): Flow<Result<AuthResult>> =
        callbackFlow {
            trySend(myFirebaseAuth.googleSignInWithCredentialAccount(credential))
            awaitClose { }
        }

    fun signInAddNewGoogleAccount(context: Context): Flow<Result<AuthResult>> =
        callbackFlow {
            trySend(myFirebaseAuth.googleSignInAddNewAccount(context))
            awaitClose { }
        }

    fun getFavoritesIdCharacters(): Flow<Result<ArrayList<Int>>> =
            myFireStoreSelects.getCharactersId(LocalAccount.currentUser!!.email)

    fun getFavoritesIdComics(): Flow<Result<ArrayList<Int>>> =
        myFireStoreSelects.getComicsId(LocalAccount.currentUser!!.email)

    fun insertFavoriteIdCharacter(idCharacter: Int) =
            myFireStoreInserts.insertCharacter(
                idUser = LocalAccount.currentUser!!.email,
               idCharacter =  idCharacter.toString()
            )
    fun insertFavoriteIdComic(idComic: Int) =
            myFireStoreInserts.insertComic(
                idUser = LocalAccount.currentUser!!.email,
                idComic =  idComic.toString()
            )

    fun deleteFavoriteIdCharacter(idCharacter: Int)=
            myFireStoreDelete.deleteCharacter(
                idUser = LocalAccount.currentUser!!.email,
                idCharacter =  idCharacter.toString()
            )
    fun deleteFavoriteIdComic(idComic: Int) =
            myFireStoreDelete.deleteComic(
                idUser = LocalAccount.currentUser!!.email,
                idComic =  idComic.toString()
            )
}