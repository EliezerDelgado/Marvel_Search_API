package com.eliezer.marvel_search_api.data.repository.comics.mock

import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject

class GetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun getListRepository(search : String) :  Comics?{
        return comicsRepository.getListTmpComics(search)
    }
}