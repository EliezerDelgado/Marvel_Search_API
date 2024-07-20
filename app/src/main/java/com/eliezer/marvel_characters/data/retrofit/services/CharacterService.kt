package com.eliezer.marvel_characters.data.retrofit.services

import com.eliezer.marvel_characters.data.retrofit.http.MarvelHttpService
import com.eliezer.marvel_characters.models.responses.CharacterWrapper
import com.eliezer.marvel_characters.data.retrofit.utils.RetrofitHash

class CharacterService(
            private val marvelHttpService: MarvelHttpService
            ){
    suspend fun listCharacter(name : String): CharacterWrapper{
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        return marvelHttpService.getCharacter(
            nameStartsWith = name,
            ts = ts,
            apikey = apiKey,
            hash = hash
        )
    }
}