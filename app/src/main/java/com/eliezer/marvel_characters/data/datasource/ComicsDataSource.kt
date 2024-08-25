package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow

interface ComicsDataSource {
    fun getDataContainer(title : String,limit : Int,offset :Int): Flow<Comics>
    fun getDataContainer(idCharacter : Int,limit : Int,offset :Int): Flow<Comics>
}