package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.ComicsDataSource
import com.eliezer.marvel_characters.data.mappers.mapToListComic
import com.eliezer.marvel_characters.data.retrofit.controllers.MarvelController
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsDataSourceImpl  @Inject constructor(
    private val marvelController: MarvelController
): ComicsDataSource {

    override fun getDataContainer(title :String,limit : Int,offset :Int): Flow<Comics> =
        marvelController.findComics(title,limit,offset).map { it.data?.mapToListComic() ?: Comics() }

    override fun getDataContainer(idChararcter: Int,limit : Int,offset :Int): Flow<Comics> =
        marvelController.findCharacterComics(idChararcter,limit,offset).map { it.data?.mapToListComic() ?: Comics()}

}