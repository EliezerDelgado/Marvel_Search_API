package com.eliezer.marvel_characters.data.repository

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.data.mappers.mapToListCharacter
import com.eliezer.marvel_characters.domain.models.Character
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val datasource: CharactersDatasource,
) : CharactersRepository {

    override fun getListCharacters(): Flow<List<Character>> {
        return datasource.getDataContainer()
            .map {
                it.mapToListCharacter()
            }
    }
}