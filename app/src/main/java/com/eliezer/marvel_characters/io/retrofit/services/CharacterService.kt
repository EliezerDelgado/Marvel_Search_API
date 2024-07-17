package com.eliezer.marvel_characters.io.retrofit.services

import com.eliezer.marvel_characters.io.retrofit.http.MarvelHttpService
import com.eliezer.marvel_characters.models.response.CharacterWrapper
import com.eliezer.marvel_characters.io.retrofit.utils.RetrofitHash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterService(
            private val marvelHttpService: MarvelHttpService
            ){
    fun listCharacter(name : String){
        val ts = System.currentTimeMillis()
        val apiKey = RetrofitHash.publicKey
        val hash = RetrofitHash.generateHash(ts)
        marvelHttpService.getCharacter(
            nameStartsWith = name,
            ts = ts,
            apikey = apiKey,
            hash = hash
        ).enqueue(object : Callback<CharacterWrapper>{
            override fun onResponse(
                p0: Call<CharacterWrapper>,
                p1: Response<CharacterWrapper>
            ) {
                //characterViewModel.character.value = p1.body()
            }

            override fun onFailure(p0: Call<CharacterWrapper>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}