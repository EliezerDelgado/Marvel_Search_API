package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertCharactersInDatabaseUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val charactersRepository: CharactersRepository
): BaseFlowUseCase<ArrayList<Character>,List<Long>?>(dispatcher) {

    override fun execute(params: ArrayList<Character>): Flow<List<Long>?> {
        return charactersRepository.setListCharacterInDatabase(params)
    }
}