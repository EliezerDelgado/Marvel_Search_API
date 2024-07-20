package com.eliezer.marvel_characters.data.retrofit.http

import com.eliezer.marvel_characters.models.responses.CharacterWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelHttpService {
    @GET(/* value = */ "/v1/public/characters")
    suspend fun  getCharacter(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

    ) : CharacterWrapper
}