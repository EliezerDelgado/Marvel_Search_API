package com.eliezer.marvel_search_api.data.datasource

import android.content.Context
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {
    fun signInGoogle(context: Context): Flow<Result<AuthResult>>
}