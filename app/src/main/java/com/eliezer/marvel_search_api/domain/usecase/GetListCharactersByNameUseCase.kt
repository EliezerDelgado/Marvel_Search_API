package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersByNameUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val charactersRepository: CharactersRepository
): BaseFlowUseCase<String, Characters>(dispatcher) {


    override fun execute(params: String): Flow<Characters> {
        return charactersRepository.getListCharactersApi(params)
    }
}