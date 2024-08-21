package com.eliezer.marvel_characters.data.repository.characters

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharactersDataSource,
) : CharactersRepository {
    private var list: HashMap<Int, List<Character>?> = HashMap()
    override fun getListTmpCharacters(): List<Character>? {
        return list[0]
    }

    override fun getListCharacters(name : String): Flow<List<Character>> {
        list[0] = null
        return datasource.getDataContainer(name)
    }


    override fun setListCharacters(id : Int,params: List<Character>) {
        list[id] = params
        list[id]?.sortedBy { it.name }
    }

    override fun getListComicCharacters(comicId: Int): Flow<List<Character>> {
        if(list[comicId]!= null)
            return flow {
                emit(
                    list[comicId]!!.toList()
                )
            }
        else
            return datasource.getDataContainer(comicId)
    }

}