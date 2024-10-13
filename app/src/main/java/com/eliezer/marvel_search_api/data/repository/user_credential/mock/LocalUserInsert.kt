package com.eliezer.marvel_search_api.data.repository.user_credential.mock
import com.eliezer.marvel_search_api.domain.repository.LocalUserCredentialRepository
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import javax.inject.Inject

class LocalUserInsert @Inject constructor(
    private val localUserCredentialRepository: LocalUserCredentialRepository
){
    fun insertCredentialOfLocalUser(myUserCredential: MyUserCredential) {
        localUserCredentialRepository.insert(myUserCredential)
    }
}