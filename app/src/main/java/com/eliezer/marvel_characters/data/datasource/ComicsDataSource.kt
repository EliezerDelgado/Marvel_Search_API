package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsDataSource {
    fun getDataContainer(title : String): Flow<List<Comic>>
    fun getDataContainer(idCharacter : Int): Flow<List<Comic>>
}