package com.eliezer.marvel_search_api.domain.repository

import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListTmpCharacters(name : String) : Characters?
    fun getListCharactersApi(name : String): Flow<Characters>
    fun getListCharactersApi(ids : ArrayList<Int>): Flow<Characters>
    fun getFavoriteListCharacters() : Flow<List<Character>?>
    fun setListTmpCharacters(id : String, params: Characters)
    fun getListCharactersByComicId (comicId : Int): Flow<Characters>
    fun setCharacterInDatabase( vararg character: Character): Flow<List<Long>?>
    fun setListCharacterInDatabase(characters: List<Character>) :  Flow<List<Long>?>
    fun deleteCharacterInDatabase(vararg character: Character)
    fun resetTmpList()
    fun clearDatabaseList() : Flow<Unit?>
}