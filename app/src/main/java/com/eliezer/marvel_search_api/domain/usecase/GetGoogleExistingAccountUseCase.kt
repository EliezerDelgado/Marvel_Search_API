package com.eliezer.marvel_search_api.domain.usecase

import android.content.Context
import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoogleExistingAccountUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val firebaseRepository: FirebaseRepository
) : BaseFlowUseCase<Context, Result<UserAccount>>(dispatcher){
    override fun execute(params: Context): Flow<Result<UserAccount>> = firebaseRepository.signInGoogleExistingAccount(params)
}