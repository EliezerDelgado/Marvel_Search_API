package com.eliezer.marvel_search_api.domain.repository
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getListTmpComics(name : String) : Comics?
    fun getListComicsApi(title : String): Flow<Comics>
    fun getListComicsApi(ids : ArrayList<Int>): Flow<Comics>
    fun getListComicsByCharacterId(characterId : Int): Flow<Comics>
    fun getFavoriteListComics() : Flow<List<Comic>?>
    fun setListTmpComics(id :String, params: Comics)
    fun setComicInDatabaseFlow(vararg comic: Comic): Flow<List<Long>?>
    fun setComicInDatabase(comic: Comic)
    fun setListComicInDatabase(comics: List<Comic>) : Flow<List<Long>?>
    fun deleteComicInDatabase(vararg comic: Comic)
    fun resetTmpList()
    fun clearDatabaseList() : Flow<Unit?>
}