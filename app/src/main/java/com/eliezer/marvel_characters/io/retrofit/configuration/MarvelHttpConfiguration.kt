package com.eliezer.marvel_characters.io.retrofit.configuration

import com.eliezer.marvel_characters.io.retrofit.http.MarvelHttpService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelHttpConfiguration {
    private companion object {
        val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }

    private fun buildCLient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildCLient())
        .build()

    fun marvelHttpService() = buildRetrofit().create(MarvelHttpService::class.java)
}
