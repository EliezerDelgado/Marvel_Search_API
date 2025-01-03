package com.eliezer.marvel_search_api.data.repository.characters

import com.eliezer.marvel_search_api.data.datasource.CharactersDataSource
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharactersDataSource,
) : CharactersRepository {
    private var list: HashMap<String, Characters?> = HashMap()
    private val characterDao get() =  LocalDatabase.db?.characterDao()

    override fun getListTmpCharacters(name : String): Characters? =
        list[name]

    override fun getListCharactersApi(name : String): Flow<Characters> {
        val offset = list[name]?.listCharacters?.size ?: 0
       return datasource.getDataContainer(name, offset)
    }

    override fun getListCharactersApi(ids: ArrayList<Int>): Flow<Characters> =
        datasource.getDataContainer(ids)
    override fun getFavoriteListCharacters(): Flow<List<Character>?> = flow{
        emit(characterDao?.getFavoriteCharacter())
    }


    override fun getListCharactersByComicId(comicId: Int): Flow<Characters> =
         datasource.getDataContainer(comicId, list[comicId.toString()]?.listCharacters?.size ?: 0)

    override fun setCharacterInDatabaseFlow(vararg character: Character) : Flow<List<Long>?> = flow{
        emit(characterDao?.insertAll(*character))
    }

    override fun setCharacterInDatabase(character: Character) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao?.insertAll(character)
        }.start()
    }

    override fun setListCharacterInDatabase(characters: List<Character>) =  setCharacterInDatabaseFlow(*characters.toTypedArray())

    override fun deleteCharacterInDatabase(vararg character: Character) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao?.delete(*character)
        }.start()
    }

    override fun resetTmpList() {
        list.clear()
    }

    override fun clearDatabaseList() =
        flow{
            emit(characterDao?.clear())
        }

    override fun setListTmpCharacters(id : String, params: Characters) {
        list[id]?.apply {
            total = params.total
            listCharacters.addAll(params.listCharacters)
        } ?: apply {
            list[id] = params
        }
    }
}