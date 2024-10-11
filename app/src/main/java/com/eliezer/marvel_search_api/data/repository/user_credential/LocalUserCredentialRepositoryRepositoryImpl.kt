package com.eliezer.marvel_search_api.data.repository.user_credential

import androidx.credentials.Credential
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.LocalUserCredentialRepository
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalUserCredentialRepositoryRepositoryImpl : LocalUserCredentialRepository {
    private val myUserCredentialDao = LocalDatabase.db?.myUserCredential()

    override fun insert(credential: Credential) {
        val myUserCredential = MyUserCredential(credential.type,credential.data)
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