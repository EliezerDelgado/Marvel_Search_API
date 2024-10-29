package com.eliezer.marvel_search_api.data.repository.comics.mock

import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun getListRepository(comic : String) :  Comics?{
        return comicsRepository.getListTmpComics(comic)
    }
}