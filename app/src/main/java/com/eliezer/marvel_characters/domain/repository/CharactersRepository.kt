package com.eliezer.marvel_characters.domain.repository

import com.eliezer.marvel_characters.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getListCharacters(): Flow<List<Character>>
}