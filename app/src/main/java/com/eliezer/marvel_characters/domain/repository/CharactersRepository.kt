package com.eliezer.marvel_characters.domain.repository

import com.eliezer.marvel_characters.models.dataclass.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListCharacters(name : String): Flow<List<Character>>
    fun setListCharacters(params: List<Character>): Flow<Int>
}