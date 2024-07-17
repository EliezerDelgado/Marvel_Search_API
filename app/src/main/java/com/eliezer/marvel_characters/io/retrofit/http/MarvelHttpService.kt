package com.eliezer.marvel_characters.io.retrofit.http

import com.eliezer.marvel_characters.models.response.CharacterWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelHttpService {
    @GET(/* value = */ "/v1/public/characters")
    fun  getCharacter(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

    ) : Call<CharacterWrapper>
}