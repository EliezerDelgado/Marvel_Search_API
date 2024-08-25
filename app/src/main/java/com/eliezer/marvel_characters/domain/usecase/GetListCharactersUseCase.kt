package com.eliezer.marvel_characters.domain.usecase

import com.eliezer.marvel_characters.core.base.BaseFlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val charactersRepository: CharactersRepository
): BaseFlowUseCase<String, Characters>(dispatcher) {


    override fun execute(params: String): Flow<Characters> {
        return charactersRepository.getListCharacters(params)
    }
}