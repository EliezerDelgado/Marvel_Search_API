package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteListComicsOnDatabaseUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val comicsRepository: ComicsRepository
): BaseFlowUseCase<Void?, List<Comic>?>(dispatcher) {

    override fun execute(params: Void?): Flow<List<Comic>?> =         comicsRepository.getFavoriteListComics()

}