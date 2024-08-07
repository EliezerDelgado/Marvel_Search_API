package com.eliezer.marvel_characters.data.repository.comics.mock

import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Comic
import javax.inject.Inject

class GetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun getListRepository() :  List<Comic>?{
        return comicsRepository.getListTmpComics()
    }
}