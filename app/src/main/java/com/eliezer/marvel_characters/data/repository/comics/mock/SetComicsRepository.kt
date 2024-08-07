package com.eliezer.marvel_characters.data.repository.comics.mock

import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Comic
import javax.inject.Inject

class SetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun setListRepository(params: List<Comic>){
        comicsRepository.setListComics(params)
    }
}