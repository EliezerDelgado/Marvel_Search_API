package com.eliezer.marvel_search_api.data.datasource

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {
    fun signInGoogleExistingAccount(context: Context): Flow<Result<AuthResult>>
    fun signInAddGoogleNewAccount(context: Context): Flow<Result<AuthResult>>
}