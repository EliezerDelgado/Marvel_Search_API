package com.eliezer.marvel_search_api.data.repository.comics.mock

import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject

class SetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository,
){
    fun setListRepository(id : String,params: Comics){
        comicsRepository.setListTmpComics(id,params)
    }
    fun setInRoomDatabase(vararg comic: Comic)
    {
            comicsRepository.setComicInDatabase(*comic)
    }
    fun deleteInRoomDatabase(vararg comic: Comic)
    {
            comicsRepository.deleteComicInDatabase(*comic)
    }
    fun setListInRoomDatabase(comics: List<Comic>)
    {
        comicsRepository.setListComicInDatabase(comics)
    }
    fun clearListRoomDatabase()
    {
        comicsRepository.clearDatabaseList()
    }

    fun resetListRepository() {
        comicsRepository.resetTmpList()
    }
}