package com.eliezer.marvel_search_api.data.retrofit.api

import com.eliezer.marvel_search_api.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_search_api.models.responses.character.CharacterResponse
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataWrapper
import com.eliezer.marvel_search_api.models.responses.comic.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMarvelHttpService {
    @GET(/* value = */ "/v1/public/characters")
    suspend fun  getCharacters(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,

    ) : CharacterDataWrapper
    @GET(/* value = */ "/v1/public/characters/{characterId}")
    suspend fun  getCharacters(
        @Path("characterId") characterId : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
    ) : CharacterResponse

    @GET(/* value = */ "/v1/public/characters")
    suspend fun  getCharacters(
        @Query("nameStartsWith") nameStartsWith : String,
        @Query("limit") limit : Int = 10,
        @Query("offset") offset : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
        ) : CharacterDataWrapper

    @GET(/* value = */ "/v1/public/characters/{characterId}/comics")
    suspend fun  getCharactersComics(
        @Path("characterId") characterId : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
        ) : ComicDataWrapper


    @GET(/* value = */ "/v1/public/characters/{characterId}/comics")
    suspend fun  getCharactersComics(
        @Path("characterId") characterId : Int,
        @Query("limit") limit : Int = 10,
        @Query("offset") offset : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
    ) : ComicDataWrapper


    @GET(/* value = */ "/v1/public/comics")
    suspend fun  getComics(
        @Query("titleStartsWith") titleStartsWith : String,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
        ) : ComicDataWrapper

    @GET(/* value = */ "/v1/public/comics")
    suspend fun  getComics(
        @Query("titleStartsWith") titleStartsWith : String,
        @Query("limit") limit : Int = 10,
        @Query("offset") offset : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
    ) : ComicDataWrapper


    @GET(/* value = */ " /v1/public/comics/{comicId}")
    suspend fun  getComics(
        @Path("comicId") comicId : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
    ) : ComicResponse

    @GET(/* value = */ " /v1/public/comics/{comicId}/characters")
    suspend fun  getComicCharacters(
        @Path("comicId") comicId : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
        ) : CharacterDataWrapper

    @GET(/* value = */ " /v1/public/comics/{comicId}/characters")
    suspend fun  getComicCharacters(
        @Path("comicId") comicId : Int,
        @Query("limit") limit : Int = 10,
        @Query("offset") offset : Int,
        @Query("ts") ts : Long,
        @Query("apikey") apikey : String,
        @Query("hash") hash : String,
    ) : CharacterDataWrapper

}