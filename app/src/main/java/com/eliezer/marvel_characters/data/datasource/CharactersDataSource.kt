package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow

interface CharactersDataSource {
    fun getDataContainer(name : String,limit : Int,offset :Int): Flow<Characters>
    fun getDataContainer(idComic : Int,limit : Int,offset :Int): Flow<Characters>
}