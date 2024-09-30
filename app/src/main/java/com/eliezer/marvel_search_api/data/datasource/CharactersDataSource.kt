package com.eliezer.marvel_search_api.data.datasource

import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow

interface CharactersDataSource {
    fun getDataContainer(name : String,offset :Int): Flow<Characters>
    fun getDataContainer(idComic : Int,offset :Int): Flow<Characters>
}