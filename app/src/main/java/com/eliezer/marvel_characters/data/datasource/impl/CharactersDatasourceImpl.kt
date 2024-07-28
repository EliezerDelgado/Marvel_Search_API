package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.data.mappers.mapToListCharacter
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.data.retrofit.controllers.MarvelController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersDatasourceImpl @Inject constructor(
    private val marvelController: MarvelController
): CharactersDatasource {

    override fun getDataContainer(name :String): Flow<List<Character>> =
     marvelController.findCharacter(name).map { it.data.mapToListCharacter() }

}