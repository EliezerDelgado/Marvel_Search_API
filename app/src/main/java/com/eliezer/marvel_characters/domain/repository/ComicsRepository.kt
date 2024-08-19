package com.eliezer.marvel_characters.domain.repository
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getListTmpComics() : List<Comic>?
    fun getListComics(title : String): Flow<List<Comic>>
    fun getListCharacterComics(characterId : Int): Flow<List<Comic>>
    fun setListComics(id :Int,params: List<Comic>)
}