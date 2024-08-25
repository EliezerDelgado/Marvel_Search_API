package com.eliezer.marvel_characters.domain.usecase

import com.eliezer.marvel_characters.core.base.BaseFlowUseCase
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListComicCharactersUseCase  @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val characterRepository: CharactersRepository
): BaseFlowUseCase<Int, Characters>(dispatcher)  {
    override fun execute(params: Int)
   : Flow<Characters>
    {
      return characterRepository.getListComicCharacters(params)
    }
}