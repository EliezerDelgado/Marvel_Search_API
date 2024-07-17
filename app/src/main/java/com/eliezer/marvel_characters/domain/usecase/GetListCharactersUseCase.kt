package com.eliezer.marvel_characters.domain.usecase

import com.eliezer.marvel_characters.core.base.FlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.data.repository.CharactersRepositoryImpl
import com.eliezer.marvel_characters.domain.models.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val characterRepository: CharactersRepositoryImpl
): FlowUseCase<Unit, List<Character>>(dispatcher) {


    override fun execute(params: Unit): Flow<List<Character>> {
        return characterRepository.getListCharacters()
    }
}