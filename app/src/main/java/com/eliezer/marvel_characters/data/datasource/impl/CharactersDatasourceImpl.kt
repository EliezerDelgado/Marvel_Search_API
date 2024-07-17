package com.eliezer.marvel_characters.data.datasource.impl

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.io.retrofit.services.CharacterService
import com.eliezer.marvel_characters.models.response.DataContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersDatasourceImpl @Inject constructor(
    private val serviceCharacter: CharacterService,
    private val name : String
): CharactersDatasource {

    override fun getDataContainer(): Flow<DataContainer> = flow {
        //emit(serviceCharacter.listCharacter(name))
    }

}