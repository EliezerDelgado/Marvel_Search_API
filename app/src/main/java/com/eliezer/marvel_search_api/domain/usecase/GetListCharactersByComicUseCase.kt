package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersByComicUseCase  @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val characterRepository: CharactersRepository
): BaseFlowUseCase<Int, Characters>(dispatcher)  {
    override fun execute(params: Int)
   : Flow<Characters>
    {
      return characterRepository.getListComicCharacters(params)
    }
}