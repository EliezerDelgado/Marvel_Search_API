package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsDataSource {
    fun getDataContainer(data : String): Flow<List<Comic>>
    fun getDataContainer(id : Int): Flow<List<Comic>>
}