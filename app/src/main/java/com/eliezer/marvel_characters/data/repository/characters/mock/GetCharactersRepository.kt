package com.eliezer.marvel_characters.data.repository.characters.mock

import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import javax.inject.Inject

class GetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun getListRepository(character : String) :  Characters?{
        return charactersRepository.getListTmpCharacters(character)
    }
}