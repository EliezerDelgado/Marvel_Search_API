package com.eliezer.marvel_characters.io.retrofit.configuration

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeneralHttpConfiguration<T> (val url : String){
    private fun buildCLient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildCLient())
        .build()

    //fun httpService() = buildRetrofit().create(T::class.java)
}