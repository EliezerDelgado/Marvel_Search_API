package com.eliezer.marvel_search_api.data.repository.user_credential

import androidx.credentials.Credential
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.LocalUserCredentialRepository
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserCredentialRepositoryImpl @Inject constructor() : LocalUserCredentialRepository{
    private val myUserCredentialDao = LocalDatabase.db?.myUserCredential()

    override fun insert(myUserCredential: MyUserCredential) {
        myUserCredentialDao?.insert(myUserCredential)
    }

    override fun getUserCredential(): Flow<MyUserCredential?> {
        return flow{
            emit(myUserCredentialDao?.getLocalUser())
        }
    }

    override fun clear() {
        myUserCredentialDao?.clear()
    }
}