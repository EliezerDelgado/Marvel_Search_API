package com.eliezer.marvel_characters.data.repository

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharactersDatasource,
) : CharactersRepository {
    private var list: List<Character>? = null
    override fun getListTmpCharacters(): List<Character>? {
        return list
    }

    override fun getListCharacters(name : String): Flow<List<Character>> {
        list = null
        return datasource.getDataContainer(name)
    }


    override fun setListCharacters(params: List<Character>) {
        list = params
        list?.sortedBy { it.name }
    }

}