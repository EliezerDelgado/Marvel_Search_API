package com.eliezer.marvel_characters.data.retrofit.api

import com.eliezer.marvel_characters.models.responses.CharacterWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMarvelHttpService {
    @GET(/* value = */ "/v1/public/characters")
    suspend fun  getCharacter(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

    ) : CharacterWrapperResponse
}