package com.eliezer.marvel_search_api.data.datasource.impl

import android.content.Context
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.data.firebase.controllers.FirebaseController
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSourceImpl @Inject constructor(private val firebaseController: FirebaseController) : FirebaseDataSource {
    override fun signInGoogle(context: Context) : Flow<Result<AuthResult>> =firebaseController.signInGoogle(context)
}