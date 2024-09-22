package com.eliezer.marvel_search_api.domain.repository

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun signInGoogleExistingAccount(context: Context) : Flow<Result<AuthResult>>
    fun signInAddGoogleNewAccount(context: Context) : Flow<Result<AuthResult>>
    fun analytics()
}