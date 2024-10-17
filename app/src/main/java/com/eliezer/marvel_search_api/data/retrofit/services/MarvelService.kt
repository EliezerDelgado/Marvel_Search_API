package com.eliezer.marvel_search_api.data.retrofit.services

import com.eliezer.marvel_search_api.data.retrofit.api.ApiMarvelHttpService
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_search_api.data.retrofit.utils.RetrofitHash
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelService @Inject constructor(
            private val apiMarvelHttpService: ApiMarvelHttpService
            ){
    suspend fun listCharacters(name : String, limit : Int, offset : Int): CharacterDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getCharacters(
            nameStartsWith = name,
            limit= limit,
            offset = offset,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
    suspend fun listCharacters(name : String): CharacterDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getCharacters(
            nameStartsWith = name,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
    suspend fun listCharacters(ids : ArrayList<Int>): CharacterDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        val result = CharacterDataWrapper()
        for(id in ids)
        {
            result.data!!.results.add(
                apiMarvelHttpService.getCharacters(
                    characterId = id,
                    ts = ts,
                    apikey = apiKey,
                    hash = hash
                )
            )
        }
        return result
    }
    suspend fun listComics(title : String,limit : Int, offset : Int): ComicDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getComics(
            titleStartsWith = title,
            limit= limit,
            offset = offset,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
    suspend fun comicById(id : Int): ComicDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getComic(
            comicId = id,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
    suspend fun characterById(id : Int): CharacterDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getCharacter(
            characterId = id,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }

    suspend fun listCharacterComics(characterId: Int,limit : Int, offset : Int): ComicDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getCharactersComics(
            characterId = characterId,
            limit= limit,
            offset = offset,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
    suspend fun listComicCharacters(comicId: Int,limit : Int, offset : Int): CharacterDataWrapper {

        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getComicCharacters(
            comicId = comicId,
            limit= limit,
            offset = offset,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
}