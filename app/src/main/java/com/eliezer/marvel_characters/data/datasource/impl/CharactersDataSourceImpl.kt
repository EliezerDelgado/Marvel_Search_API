package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.data.mappers.mapToListCharacter
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.data.retrofit.controllers.MarvelController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataSourceImpl @Inject constructor(
    private val marvelController: MarvelController
): CharactersDataSource {

    override fun getDataContainer(name :String): Flow<List<Character>> =
     marvelController.findCharacters(name).map { it.data?.mapToListCharacter() ?: emptyList() }

    override fun getDataContainer(idComic: Int): Flow<List<Character>> =
        marvelController.findComicCharacters(idComic).map { it.data?.mapToListCharacter() ?: emptyList() }


}