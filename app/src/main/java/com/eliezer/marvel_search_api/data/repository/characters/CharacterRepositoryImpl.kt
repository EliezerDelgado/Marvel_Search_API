package com.eliezer.marvel_search_api.data.repository.characters

import android.database.sqlite.SQLiteConstraintException
import com.eliezer.marvel_search_api.data.const.API_SEARCH_LIMIT
import com.eliezer.marvel_search_api.data.datasource.CharactersDataSource
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.CharactersRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun setCharacterInDatabase(vararg character: Character) : Flow<List<Long>?> = flow{
        emit(characterDao?.insert(*character))
    }
    override fun setListCharacterInDatabase(characters: List<Character>) =  setCharacterInDatabase(*characters.toTypedArray())

    override fun deleteCharacterInDatabase(vararg character: Character) {
        Thread{
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
            //ToDO Es el mismo total cuando se añade la continuacion de la lista de characters = / +=
            total = params.total
            listCharacters.addAll(params.listCharacters)
        } ?: apply {
            list[id] = params
        }
    }
}