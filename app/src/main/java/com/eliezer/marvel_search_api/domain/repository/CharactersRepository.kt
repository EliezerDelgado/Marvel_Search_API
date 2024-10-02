package com.eliezer.marvel_search_api.domain.repository

import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListTmpCharacters(name : String) : Characters?
    fun getListCharacters(name : String): Flow<Characters>
    fun setListCharacters(id : String,params: Characters)
    fun getListCharacters(ids : ArrayList<Int>): Flow<Characters>
    fun getListComicCharacters (comicId : Int): Flow<Characters>
    fun resetList()
}