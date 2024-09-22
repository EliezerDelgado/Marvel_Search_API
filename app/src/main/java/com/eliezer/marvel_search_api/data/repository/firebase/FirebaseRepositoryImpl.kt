package com.eliezer.marvel_search_api.data.repository.firebase

import android.content.Context
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val datasource: FirebaseDataSource,
) : FirebaseRepository {
    override fun signInGoogle(context: Context) : Flow<Result<AuthResult>> = datasource.signInGoogle(context)

    override fun analytics() {
    }
}