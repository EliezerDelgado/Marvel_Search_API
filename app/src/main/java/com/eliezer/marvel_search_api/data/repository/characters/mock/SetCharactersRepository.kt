package com.eliezer.marvel_search_api.data.repository.characters.mock

import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun setListTmpCharacters(id :String, params: Characters){
        charactersRepository.setListTmpCharacters(id,params)
    }
    fun setInRoomDatabase(character: Character)
    {
            charactersRepository.setCharacterInDatabase(character)
    }

    fun deleteInRoomDatabase(vararg character: Character)
    {
            charactersRepository.deleteCharacterInDatabase(*character)
    }
}