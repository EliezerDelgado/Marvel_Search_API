package com.eliezer.marvel_search_api.data.retrofit.configuration

import com.eliezer.marvel_search_api.data.retrofit.api.ApiMarvelHttpService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelHttpConfiguration @Inject constructor() {
    private companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }

    private fun buildClient() = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    fun marvelHttpService(): ApiMarvelHttpService =
        buildRetrofit().create(ApiMarvelHttpService::class.java)
}
