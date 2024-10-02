package com.eliezer.marvel_search_api.data.retrofit.controllers

import com.eliezer.marvel_search_api.data.const.API_SEARCH_LIMIT
import com.eliezer.marvel_search_api.data.retrofit.services.CharacterService
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelController @Inject constructor(
    private val characterService: CharacterService
) {
    fun findCharacters(name : String, offset : Int): Flow<CharacterDataWrapper> =
        flow {
            emit(        characterService.listCharacters(name,API_SEARCH_LIMIT,offset)
            )
        }
    fun findComics(title : String, offset : Int): Flow<ComicDataWrapper> =
        flow {
            emit(        characterService.listComics(title,API_SEARCH_LIMIT,offset)
            )
        }

    fun findComicsOffCharacter(characterId: Int, offset : Int):  Flow<ComicDataWrapper> =
        flow {
            emit(        characterService.listCharacterComics(characterId, API_SEARCH_LIMIT,offset)
            )
        }
    fun findComicCharacters(comicId: Int, offset : Int):  Flow<CharacterDataWrapper> =
        flow {
            emit(        characterService.listComicCharacters(comicId,API_SEARCH_LIMIT,offset)
            )
        }
    fun findComicsOffIds(ids : ArrayList<Int>)=
        flow {
                emit(
                    characterService.listComics(ids)
                )
        }
    fun findCharactersOffIds(ids : ArrayList<Int>)=
        flow {
            emit(
                characterService.listCharacters(ids)
            )
        }

}