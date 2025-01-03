package com.eliezer.marvel_search_api.data.repository.comics.mock

import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository,
){
    fun setListTmpComics(id : String, params: Comics){
        comicsRepository.setListTmpComics(id,params)
    }
    fun setInRoomDatabase(comic: Comic)
    {
            comicsRepository.setComicInDatabase(comic)
    }
    fun deleteInRoomDatabase(vararg comic: Comic)
    {
            comicsRepository.deleteComicInDatabase(*comic)
    }
}