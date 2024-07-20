package com.eliezer.marvel_characters.data.retrofit.controllers

import com.eliezer.marvel_characters.data.retrofit.services.CharacterService
import com.eliezer.marvel_characters.models.responses.CharacterWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarvelController(
    private var characterService: CharacterService
) {
    fun findCharacter(name : String): Flow<CharacterWrapper> =
        flow {
            emit(        characterService.listCharacter(name)
            )
        }
}