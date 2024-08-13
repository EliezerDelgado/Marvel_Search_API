package com.eliezer.marvel_characters.data.repository.comics

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.data.datasource.ComicsDataSource
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepositoryImpl @Inject constructor(
    private val datasource: ComicsDataSource,
) : ComicsRepository {
    private var list: List<Comic>? = null



    override fun getListTmpComics(): List<Comic>? {
        return list
    }

    override fun getListComics(title: String): Flow<List<Comic>> {
        list = null
        return datasource.getDataContainer(title)
    }

    override fun getListCharacterComics(characterId: Int): Flow<List<Comic>> {

        list = null
        return datasource.getDataContainer(characterId)
    }

    override fun setListComics(params: List<Comic>) {
        list = params
        list?.sortedBy { it.title }
    }

}