package com.eliezer.marvel_search_api.data.retrofit.controllers

import com.eliezer.marvel_search_api.data.const.API_SEARCH_LIMIT
import com.eliezer.marvel_search_api.data.retrofit.services.MarvelService
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataWrapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelController @Inject constructor(
    private val marvelService: MarvelService
) {
    fun findCharacters(name : String, offset : Int): Flow<CharacterDataWrapper> =
        flow {
            emit(        marvelService.listCharacters(name,API_SEARCH_LIMIT,offset)
            )
        }
    fun findComics(title : String, offset : Int): Flow<ComicDataWrapper> =
        flow {
            emit(        marvelService.listComics(title,API_SEARCH_LIMIT,offset)
            )
        }

    fun findComicsOffCharacter(characterId: Int, offset : Int):  Flow<ComicDataWrapper> =
        flow {
            emit(        marvelService.listCharacterComics(characterId, API_SEARCH_LIMIT,offset)
            )
        }
    fun findComicCharacters(comicId: Int, offset : Int):  Flow<CharacterDataWrapper> =
        flow {
            emit(        marvelService.listComicCharacters(comicId,API_SEARCH_LIMIT,offset)
            )
        }
    fun findComicsOffIds(ids : ArrayList<Int>)=
        flow{
            val comics = ComicDataWrapper()
            for (id in ids) {
                comics.data.results.add(marvelService.listComics(id).data.results[0])
            }
            emit(comics)
        }
    fun findCharactersOffIds(ids : ArrayList<Int>)=
        flow {
            emit(
                marvelService.listCharacters(ids)
            )
        }

}