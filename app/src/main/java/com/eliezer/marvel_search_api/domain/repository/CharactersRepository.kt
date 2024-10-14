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
    fun setListCharacterInDatabase(vararg character: Character)
    fun setListCharacterInDatabase(characters: List<Character>)
    fun resetTmpList()
    fun clearDatabaseList()
}