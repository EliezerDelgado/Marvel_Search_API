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
    fun setInRoomDatabase(vararg character: Character)
    {
        character.forEach {
            charactersRepository.setCharacterInDatabase(it)
        }
    }

    fun deleteInRoomDatabase(vararg character: Character)
    {
        character.forEach {
            charactersRepository.deleteCharacterInDatabase(it)
        }
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