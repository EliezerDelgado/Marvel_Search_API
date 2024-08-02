package com.eliezer.marvel_characters.data.retrofit.configuration

import com.eliezer.marvel_characters.data.retrofit.api.ApiMarvelHttpService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelHttpConfiguration @Inject constructor() {
    private companion object {
        val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }

    private fun buildCLient() = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildCLient())
        .build()

    fun marvelHttpService() = buildRetrofit().create(ApiMarvelHttpService::class.java)
}
