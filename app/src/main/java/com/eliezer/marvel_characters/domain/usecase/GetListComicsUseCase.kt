package com.eliezer.marvel_characters.domain.usecase

import com.eliezer.marvel_characters.core.base.BaseFlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListComicsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val comicsRepository: ComicsRepository
): BaseFlowUseCase<String, Comics>(dispatcher) {


    override fun execute(params: String): Flow<Comics> {
        return comicsRepository.getListComics(params)
    }
}