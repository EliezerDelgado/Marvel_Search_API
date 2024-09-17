package com.eliezer.marvel_search_api.data.repository.comics

import com.eliezer.marvel_search_api.data.const.API_SEARCH_LIMIT
import com.eliezer.marvel_search_api.data.datasource.ComicsDataSource
import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepositoryImpl @Inject constructor(
    private val datasource: ComicsDataSource,
) : ComicsRepository {
    private var list: HashMap<String, Comics?> = HashMap()



    override fun getListTmpComics(name : String):Comics? =
         list.get(name)

    override fun getListComics(title: String): Flow<Comics> =
        datasource.getDataContainer(title, API_SEARCH_LIMIT,list[title]?.listComics?.size ?: 0)


    override fun getListCharacterComics(characterId: Int): Flow<Comics> =
        datasource.getDataContainer(characterId, API_SEARCH_LIMIT,list[characterId.toString()]?.listComics?.size ?:0)


    override fun setListComics(id :String,params: Comics) {
        list[id]?.apply {
            total = params.total
            listComics.addAll(params.listComics)
        } ?: apply {
            list[id] = params
        }
    }
    override fun resetList() {
        list.clear()
    }

}