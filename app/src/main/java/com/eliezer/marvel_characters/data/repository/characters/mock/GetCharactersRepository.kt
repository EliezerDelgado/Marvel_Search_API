package com.eliezer.marvel_characters.data.repository.characters.mock

import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import javax.inject.Inject

class GetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun getListRepository() :  List<Character>?{
        return charactersRepository.getListTmpCharacters()
    }
}