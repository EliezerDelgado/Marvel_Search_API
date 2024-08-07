package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Character
import kotlinx.coroutines.flow.Flow

interface CharactersDataSource {
    fun getDataContainer(name : String): Flow<List<Character>>
}