package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.ComicsDataSource
import com.eliezer.marvel_characters.data.mappers.mapToListCharacter
import com.eliezer.marvel_characters.data.mappers.mapToListComic
import com.eliezer.marvel_characters.data.retrofit.controllers.MarvelController
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsDataSourceImpl  @Inject constructor(
    private val marvelController: MarvelController
): ComicsDataSource {

    override fun getDataContainer(title :String): Flow<List<Comic>> =
        marvelController.findComics(title).map { it.data?.mapToListComic() ?: emptyList() }

}