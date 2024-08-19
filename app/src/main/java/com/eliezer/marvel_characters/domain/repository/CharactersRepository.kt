package com.eliezer.marvel_characters.domain.repository

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListTmpCharacters() : List<Character>?
    fun getListCharacters(name : String): Flow<List<Character>>
    fun setListCharacters(id : Int,params: List<Character>)
    fun getListComicCharacters (comicId : Int): Flow<List<Character>>
}