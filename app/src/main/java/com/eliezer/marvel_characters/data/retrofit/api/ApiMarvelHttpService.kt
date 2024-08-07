package com.eliezer.marvel_characters.data.retrofit.api

import com.eliezer.marvel_characters.models.responses.character.CharacterWrapperResponse
import com.eliezer.marvel_characters.models.responses.comic.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMarvelHttpService {
    @GET(/* value = */ "/v1/public/characters")
    suspend fun  getCharacters(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

    ) : CharacterWrapperResponse

    @GET(/* value = */ "/v1/public/comics")
    suspend fun  getComics(
        @Query("titleStartsWith") titleStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

        ) : ComicDataWrapper
}