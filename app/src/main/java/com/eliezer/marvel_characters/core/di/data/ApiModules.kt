package com.eliezer.marvel_characters.core.di.data

import com.eliezer.marvel_characters.data.retrofit.configuration.MarvelHttpConfiguration
import com.eliezer.marvel_characters.data.retrofit.http.MarvelHttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModules {

    @Provides
    fun provideApiHotels(retrofit: MarvelHttpConfiguration): MarvelHttpService {
        return retrofit.marvelHttpService()
    }
}