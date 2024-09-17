package com.eliezer.marvel_search_api.data.repository.characters.mock

import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import javax.inject.Inject

class GetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun getListRepository(character : String) :  Characters?{
        return charactersRepository.getListTmpCharacters(character)
    }
}