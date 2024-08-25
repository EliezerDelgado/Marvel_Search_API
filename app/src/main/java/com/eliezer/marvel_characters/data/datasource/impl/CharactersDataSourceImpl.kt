package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.data.mappers.mapToListCharacter
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.data.retrofit.controllers.MarvelController
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataSourceImpl @Inject constructor(
    private val marvelController: MarvelController
): CharactersDataSource {

    override fun getDataContainer(name :String,limit : Int,offset :Int): Flow<Characters> =
     marvelController.findCharacters(name,limit,offset).map { it.data?.mapToListCharacter() ?: Characters() }

    override fun getDataContainer(idComic: Int,limit : Int,offset :Int): Flow<Characters> =
        marvelController.findComicCharacters(idComic,limit,offset).map { it.data?.mapToListCharacter() ?: Characters()}


}