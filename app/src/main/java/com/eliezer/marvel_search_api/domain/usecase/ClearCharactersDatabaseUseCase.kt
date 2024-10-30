package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ClearCharactersDatabaseUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val charactersRepository: CharactersRepository
) : BaseFlowUseCase<Void?, Unit?>(dispatcher){
    override fun execute(params:Void?): Flow<Unit?> = charactersRepository.clearDatabaseList()
}