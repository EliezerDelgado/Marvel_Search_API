package com.eliezer.marvel_characters.data.repository.characters.mock

import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
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