package com.eliezer.marvel_characters.data.retrofit.services

import com.eliezer.marvel_characters.data.retrofit.api.ApiMarvelHttpService
import com.eliezer.marvel_characters.models.responses.character.CharacterWrapperResponse
import com.eliezer.marvel_characters.data.retrofit.utils.RetrofitHash
import com.eliezer.marvel_characters.models.responses.comic.ComicDataWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterService @Inject constructor(
            private val apiMarvelHttpService: ApiMarvelHttpService
            ){
    suspend fun listCharacter(name : String): CharacterWrapperResponse {
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
    suspend fun listComics(title : String): ComicDataWrapper {
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return apiMarvelHttpService.getComics(
            titleStartsWith = title,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
}