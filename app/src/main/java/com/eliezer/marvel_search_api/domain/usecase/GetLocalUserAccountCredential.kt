package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.LocalUserCredentialRepository
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalUserAccountCredential  @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val localUserCredentialRepository : LocalUserCredentialRepository
): BaseFlowUseCase<Void?, MyUserCredential?>(dispatcher)  {
    override fun execute(params: Void?)
            : Flow<MyUserCredential?> =
        localUserCredentialRepository.getUserCredential()
}