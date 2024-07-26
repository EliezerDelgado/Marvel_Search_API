package com.eliezer.marvel_characters.core.di.data

import com.eliezer.marvel_characters.data.retrofit.configuration.MarvelHttpConfiguration
import com.eliezer.marvel_characters.data.retrofit.http.MarvelHttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApiModules {

    @Provides
    fun provideApiHotels(retrofit: MarvelHttpConfiguration): MarvelHttpService {
        return retrofit.marvelHttpService()
    }
}