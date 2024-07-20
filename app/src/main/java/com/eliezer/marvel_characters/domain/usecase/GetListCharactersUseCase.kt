package com.eliezer.marvel_characters.domain.usecase

import com.eliezer.marvel_characters.core.base.FlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.data.repository.MarvelRepositoryImpl
import com.eliezer.marvel_characters.models.dataclass.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val marvelRepository: MarvelRepositoryImpl
): FlowUseCase<String, List<Character>>(dispatcher) {


    override fun execute(params: String): Flow<List<Character>> {
        return marvelRepository.getListCharacters(params)
    }
}