package com.eliezer.marvel_search_api.domain.usecase

import android.content.Context
import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirebaseResultSignInGoogleUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val firebaseRepository: FirebaseRepository
) : BaseFlowUseCase<Context, Result<AuthResult>>(dispatcher){
    override fun execute(params: Context): Flow<Result<AuthResult>> = firebaseRepository.signInGoogle(params)
}