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
    private val myUserCredentialDao get() = LocalDatabase.db?.myUserCredential()

    override fun insert(myUserCredential: MyUserCredential) {
        Thread{
            try {
                myUserCredentialDao?.insert(myUserCredential)
            }
            catch (_ :Exception)
            {
                myUserCredentialDao?.update(myUserCredential)
            }
        }.start()
    }

    override fun getUserCredential(): Flow<MyUserCredential?> =flow{
            emit(myUserCredentialDao?.getLocalUser())
        }


    override fun clear() {
        Thread {
            myUserCredentialDao?.clear()
        }.start()
    }
}