package com.eliezer.marvel_search_api.data.repository.comics

import com.eliezer.marvel_search_api.data.datasource.ComicsDataSource
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepositoryImpl @Inject constructor(
    private val datasource: ComicsDataSource
) : ComicsRepository {
    private var list: HashMap<String, Comics?> = HashMap()
    private val comicDao get() =    LocalDatabase.db?.comicDao()

    override fun getListTmpComics(name : String):Comics? =
        list[name]

    override fun getListComicsApi(title: String): Flow<Comics> =
        datasource.getDataContainer(title, list[title]?.listComics?.size ?: 0)

    override fun getListComicsApi(ids: ArrayList<Int>): Flow<Comics> =
        datasource.getDataContainer(ids)


    override fun getListComicsByCharacterId(characterId: Int): Flow<Comics> =
        datasource.getDataContainer(characterId, list[characterId.toString()]?.listComics?.size ?:0)

    override fun getFavoriteListComics() : Flow<List<Comic>?> = flow{
        emit(comicDao?.getFavoriteComic())
    }


    override fun setListTmpComics(id :String, params: Comics) {
        list[id]?.apply {
            total = params.total
            listComics.addAll(params.listComics)
        } ?: apply {
            list[id] = params
        }
    }

    override fun setComicInDatabaseFlow(vararg comic: Comic): Flow<List<Long>?> = flow {
        emit(comicDao?.insertAll(*comic))
    }

    override fun setComicInDatabase(comic: Comic)  {
        CoroutineScope(Dispatchers.IO).launch {
            comicDao?.insert(comic)
        }.start()
    }


    override fun setListComicInDatabase(comics: List<Comic>) : Flow<List<Long>?> = setComicInDatabaseFlow(*comics.toTypedArray())


    override fun deleteComicInDatabase(vararg comic: Comic) {
        Thread{
            comicDao?.delete(*comic)
        }.start()
    }

    override fun resetTmpList() {
        list.clear()
    }

    override fun clearDatabaseList() =
        flow {
            emit(comicDao?.clear())
        }

}