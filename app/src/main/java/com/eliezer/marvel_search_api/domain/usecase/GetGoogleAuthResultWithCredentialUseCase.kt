package com.eliezer.marvel_search_api.domain.usecase

import androidx.credentials.Credential
import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoogleAuthResultWithCredentialUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val firebaseRepository : FirebaseRepository
) : BaseFlowUseCase<Credential, Result<AuthResult>>(dispatcher){
    override fun execute(params: Credential): Flow<Result<AuthResult>> = firebaseRepository.signInWithCredentialsGoogleAccount(params)
}