package com.eliezer.marvel_characters.data.repository.characters

import com.eliezer.marvel_characters.data.const.API_SEARCH_LIMIT
import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharactersDataSource,
) : CharactersRepository {
    private var list: HashMap<String, Characters?> = HashMap()
    override fun getListTmpCharacters(name : String): Characters? =
         list.get(name)

    override fun getListCharacters(name : String): Flow<Characters> {
        var s = list[name]?.listCharacters?.size ?: 0
       return datasource.getDataContainer(name, API_SEARCH_LIMIT,s)

    }


   override fun getListComicCharacters(comicId: Int): Flow<Characters> =
         datasource.getDataContainer(comicId, API_SEARCH_LIMIT,list[comicId.toString()]?.listCharacters?.size ?: 0)

    override fun setListCharacters(id : String,params: Characters) {
        list[id.toString()]?.apply {
            total = params.total
            listCharacters.addAll(params.listCharacters)
        } ?: apply {
            list[id] = params
        }
    }
}