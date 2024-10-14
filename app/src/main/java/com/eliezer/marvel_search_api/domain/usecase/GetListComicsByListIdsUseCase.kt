package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetListComicsByListIdsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val comicsRepository: ComicsRepository
): BaseFlowUseCase<ArrayList<Int>, Comics>(dispatcher) {


    override fun execute(params: ArrayList<Int>): Flow<Comics> = comicsRepository.getListComicsApi(params)

}