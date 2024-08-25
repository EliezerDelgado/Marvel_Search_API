package com.eliezer.marvel_characters.data.repository.comics.mock

import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import javax.inject.Inject

class GetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun getListRepository(search : String) :  Comics?{
        return comicsRepository.getListTmpComics(search)
    }
}