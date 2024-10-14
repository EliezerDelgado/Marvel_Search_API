package com.eliezer.marvel_search_api.data.datasource

import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow

interface ComicsDataSource {
    fun getDataContainer(title : String,offset :Int): Flow<Comics>
    fun getDataContainer(idCharacter : Int,offset :Int): Flow<Comics>
    fun getDataContainer(ids : ArrayList<Int>): Flow<Comics>
}