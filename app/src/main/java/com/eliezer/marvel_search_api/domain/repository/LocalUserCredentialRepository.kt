package com.eliezer.marvel_search_api.domain.repository

import androidx.credentials.Credential
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import kotlinx.coroutines.flow.Flow

interface LocalUserCredentialRepository {
    fun insert(myUserCredential: MyUserCredential)
    fun getUserCredential() : Flow<MyUserCredential?>
    fun clear()
}