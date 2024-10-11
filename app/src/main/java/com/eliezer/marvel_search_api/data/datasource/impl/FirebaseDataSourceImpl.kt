package com.eliezer.marvel_search_api.data.datasource.impl

import android.content.Context
import androidx.credentials.Credential
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.data.firebase.controllers.FirebaseController
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSourceImpl @Inject constructor(
    private val firebaseController: FirebaseController
) : FirebaseDataSource {
    override fun signInGoogleExistingAccount(context: Context) : Flow<Result<AuthResult>> =firebaseController.signInExistingGoogleAccount(context)
    override fun signInAddGoogleNewAccount(context: Context) : Flow<Result<AuthResult>> =firebaseController.signInAddNewGoogleAccount(context)
    override fun getFavoriteIdCharacters(): Flow<Result<ArrayList<Int>>> =
        firebaseController.getFavoritesIdCharacters()

    override fun getFavoriteIdComics(): Flow<Result<ArrayList<Int>>> =
    firebaseController.getFavoritesIdComics()

    override fun insertFavoriteIdCharacter(idCharacter: Int) =
        firebaseController.insertFavoriteIdCharacter(idCharacter)

    override fun insertFavoriteIdComic(idComic: Int) =
        firebaseController.insertFavoriteIdComic(idComic)

    override fun deleteFavoriteIdCharacter(idCharacter: Int) =
        firebaseController.deleteFavoriteIdCharacter(idCharacter)

    override fun deleteFavoriteIdComic(idComic: Int) =
        firebaseController.deleteFavoriteIdComic(idComic)

    override fun signInWithCredentialsGoogleAccount(credential: Credential): Flow<Result<AuthResult>> =
        firebaseController.signInWithCredentialsGoogleAccount(credential)

}