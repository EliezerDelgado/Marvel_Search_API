package com.eliezer.marvel_search_api.data.repository.characters.mock

import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import javax.inject.Inject

class SetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun setListRepository(id :String,params: Characters){
        charactersRepository.setListCharacters(id,params)
    }
    fun resetListRepository()
    {
        charactersRepository.resetList()
    }
}