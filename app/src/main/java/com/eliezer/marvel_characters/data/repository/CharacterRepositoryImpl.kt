package com.eliezer.marvel_characters.data.repository

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharactersDatasource,
) : CharactersRepository {

    override fun getListCharacters(name : String): Flow<List<Character>> {
        return datasource.getDataContainer(name)
    }

}