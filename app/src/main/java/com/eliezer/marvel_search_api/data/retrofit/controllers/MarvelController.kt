package com.eliezer.marvel_search_api.data.retrofit.controllers

import com.eliezer.marvel_search_api.data.retrofit.services.CharacterService
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelController @Inject constructor(
    private var characterService: CharacterService
) {
    fun findCharacters(name : String,limit : Int, offset : Int): Flow<CharacterDataWrapper> =
        flow {
            emit(        characterService.listCharacter(name,limit,offset)
            )
        }
    fun findComics(title : String,limit : Int, offset : Int): Flow<ComicDataWrapper> =
        flow {
            emit(        characterService.listComics(title,limit,offset)
            )
        }

    fun findCharacterComics(characterId: Int,limit : Int, offset : Int):  Flow<ComicDataWrapper> =
        flow {
            emit(        characterService.listCharacterComics(characterId,limit,offset)
            )
        }
    fun findComicCharacters(comicId: Int,limit : Int, offset : Int):  Flow<CharacterDataWrapper> =
        flow {
            emit(        characterService.listComicCharacters(comicId,limit,offset)
            )
        }

}