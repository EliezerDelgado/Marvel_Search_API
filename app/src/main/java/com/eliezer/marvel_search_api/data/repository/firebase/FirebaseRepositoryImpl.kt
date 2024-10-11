package com.eliezer.marvel_search_api.data.repository.firebase

import android.content.Context
import androidx.credentials.Credential
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val datasource: FirebaseDataSource,
) : FirebaseRepository {

    private val characterDao = LocalDatabase.db?.characterDao()
    private val comicDao = LocalDatabase.db?.comicDao()

    override fun signInGoogleExistingAccount(context: Context) : Flow<Result<AuthResult>> = datasource.signInGoogleExistingAccount(context)
    override fun signInAddGoogleNewAccount(context: Context) : Flow<Result<AuthResult>> = datasource.signInAddGoogleNewAccount(context)
    override fun getFavoriteIdCharacters(): Flow<Result<ArrayList<Int>>> =  datasource.getFavoriteIdCharacters()

    override fun getFavoriteIdComics(): Flow<Result<ArrayList<Int>>> = datasource.getFavoriteIdComics()
    override fun insertFavoriteIdCharacter(idCharacter: Int) =
        datasource.insertFavoriteIdCharacter(idCharacter)

    override fun insertFavoriteIdComic(idComic: Int) =
        datasource.insertFavoriteIdComic(idComic)

    override fun deleteFavoriteIdCharacter(idCharacter: Int) =
        datasource.deleteFavoriteIdCharacter(idCharacter)

    override fun deleteFavoriteIdComic(idComic: Int) =
        datasource.deleteFavoriteIdComic(idComic)
    override fun analytics() {
    }

    override fun signInWithCredentialsGoogleAccount(credential: Credential): Flow<Result<AuthResult>> =
        datasource.signInWithCredentialsGoogleAccount(credential)
}