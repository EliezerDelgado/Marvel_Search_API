package com.eliezer.marvel_characters.data.retrofit.controllers

import com.eliezer.marvel_characters.data.retrofit.services.CharacterService
import com.eliezer.marvel_characters.models.responses.character.CharacterWrapperResponse
import com.eliezer.marvel_characters.models.responses.comic.ComicDataContainer
import com.eliezer.marvel_characters.models.responses.comic.ComicDataWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelController @Inject constructor(
    private var characterService: CharacterService
) {
    fun findCharacters(name : String): Flow<CharacterWrapperResponse> =
        flow {
            emit(        characterService.listCharacter(name)
            )
        }
    fun findComics(title : String): Flow<ComicDataWrapper> =
        flow {
            emit(        characterService.listComics(title)
            )
        }
}