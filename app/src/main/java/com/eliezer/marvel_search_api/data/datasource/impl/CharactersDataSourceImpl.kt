package com.eliezer.marvel_search_api.data.datasource.impl

import com.eliezer.marvel_search_api.data.datasource.CharactersDataSource
import com.eliezer.marvel_search_api.data.mappers.mapToListCharacter
import com.eliezer.marvel_search_api.data.retrofit.controllers.MarvelController
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataSourceImpl @Inject constructor(
    private val marvelController: MarvelController
): CharactersDataSource {

    override fun getDataContainer(name :String,offset :Int): Flow<Characters> =
     marvelController.findCharacters(name,offset).map { it.data?.mapToListCharacter() ?: Characters() }

    override fun getDataContainer(idComic: Int,offset :Int): Flow<Characters> =
        marvelController.findComicCharacters(idComic,offset).map { it.data?.mapToListCharacter() ?: Characters()}

    override fun getDataContainer(ids: ArrayList<Int>): Flow<Characters> = marvelController.findCharactersOffIds(ids).map { it.data?.mapToListCharacter() ?: Characters()}
}