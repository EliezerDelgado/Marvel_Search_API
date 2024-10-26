package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetResultsInsertComicsInDatabaseUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val comicsRepository: ComicsRepository
): BaseFlowUseCase<ArrayList<Comic>, List<Long>?>(dispatcher) {
    override fun execute(params: ArrayList<Comic>): Flow<List<Long>?> {
        return comicsRepository.setListComicInDatabase(params)
    }
}