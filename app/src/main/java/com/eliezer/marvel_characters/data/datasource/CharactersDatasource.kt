package com.eliezer.marvel_characters.data.datasource

import com.eliezer.marvel_characters.models.response.DataContainer
import kotlinx.coroutines.flow.Flow

interface CharactersDatasource {
    fun getDataContainer(): Flow<DataContainer>
}