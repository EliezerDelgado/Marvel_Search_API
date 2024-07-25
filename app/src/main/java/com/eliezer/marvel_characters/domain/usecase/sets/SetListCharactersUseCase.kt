package com.eliezer.marvel_characters.domain.usecase.sets

import com.eliezer.marvel_characters.core.base.FlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetListCharactersUseCase@Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val marvelRepository: CharactersRepository
): FlowUseCase<List<Character>, Int>(dispatcher) {
    override fun execute(params: List<Character>): Flow<Int> {
        return marvelRepository.setListCharacters(params)
    }
}