package com.eliezer.marvel_search_api.core.di

import com.eliezer.marvel_search_api.data.retrofit.configuration.MarvelHttpConfiguration
import com.eliezer.marvel_search_api.data.retrofit.api.ApiMarvelHttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApiModules {

    @Provides
    fun provideApiHotels(retrofit: MarvelHttpConfiguration): ApiMarvelHttpService {
        return retrofit.marvelHttpService()
    }
}