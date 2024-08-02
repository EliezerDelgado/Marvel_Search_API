package com.eliezer.marvel_characters.data.repository.mock

import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import javax.inject.Inject

class SetCharactersRepository @Inject constructor(
    private val marvelRepository: CharactersRepository
){
    fun setListRepository(params: List<Character>){
        marvelRepository.setListCharacters(params)
    }
}