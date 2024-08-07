package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsDataSource {
    fun getDataContainer(name : String): Flow<List<Comic>>
}