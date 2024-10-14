package com.eliezer.marvel_search_api.data.repository.characters.mock

import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
import javax.inject.Inject

class SetCharactersRepository @Inject constructor(
    private val charactersRepository: CharactersRepository
){
    fun setListRepository(id :String,params: Characters){
        charactersRepository.setListTmpCharacters(id,params)
    }
    fun setListRoomDatabase(characters: List<Character>)
    {
        charactersRepository.setListCharacterInDatabase(characters)
    }
    fun clearListRoomDatabase()
    {
        charactersRepository.clearDatabaseList()
    }

    fun resetListRepository()
    {
        charactersRepository.resetTmpList()
    }
}