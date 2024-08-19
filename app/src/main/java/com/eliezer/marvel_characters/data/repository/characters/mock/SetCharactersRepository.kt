package com.eliezer.marvel_characters.data.repository.characters.mock

import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import javax.inject.Inject

class SetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun setListRepository(id :Int,params: List<Character>){
        charactersRepository.setListCharacters(id,params)
    }
}