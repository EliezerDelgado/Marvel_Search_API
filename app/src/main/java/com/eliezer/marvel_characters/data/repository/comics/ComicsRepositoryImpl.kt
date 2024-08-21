package com.eliezer.marvel_characters.data.repository.comics

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.data.datasource.ComicsDataSource
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepositoryImpl @Inject constructor(
    private val datasource: ComicsDataSource,
) : ComicsRepository {
    private var list: HashMap<Int, List<Comic>?> = HashMap()



    override fun getListTmpComics(): List<Comic>? {
        return list[0]
    }

    override fun getListComics(title: String): Flow<List<Comic>> {
        list[0] = null
        return datasource.getDataContainer(title)
    }

    override fun getListCharacterComics(characterId: Int): Flow<List<Comic>> {

        if(list[characterId]!= null)
            return flow {
                emit(
                    list[characterId]!!.toList()
                )
            }
        else
            return datasource.getDataContainer(characterId)
    }

    override fun setListComics(id :Int,params: List<Comic>) {
        list[id] = params
        list[id]?.sortedBy { it.title }
    }

}