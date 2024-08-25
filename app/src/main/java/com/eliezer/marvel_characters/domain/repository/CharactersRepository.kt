package com.eliezer.marvel_characters.domain.repository

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListTmpCharacters(name : String) : Characters?
    fun getListCharacters(name : String): Flow<Characters>
    fun setListCharacters(id : String,params: Characters)
    fun getListComicCharacters (comicId : Int): Flow<Characters>
}