package com.eliezer.marvel_search_api.data.firebase.controllers

import android.content.Context
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAuth
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseController @Inject constructor(
    private val myFirebaseAuth: MyFirebaseAuth
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
}