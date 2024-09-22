package com.eliezer.marvel_search_api.core.di

import com.eliezer.marvel_search_api.data.datasource.CharactersDataSource
import com.eliezer.marvel_search_api.data.datasource.ComicsDataSource
import com.eliezer.marvel_search_api.data.datasource.FirebaseDataSource
import com.eliezer.marvel_search_api.data.datasource.impl.CharactersDataSourceImpl
import com.eliezer.marvel_search_api.data.datasource.impl.ComicsDataSourceImpl
import com.eliezer.marvel_search_api.data.datasource.impl.FirebaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun bindingCharactersDatasource(charactersDataSourceImpl: CharactersDataSourceImpl):CharactersDataSource

    @Binds
    abstract fun bindingComicsDatasource(comicsDataSourceImpl : ComicsDataSourceImpl): ComicsDataSource

    @Binds
    abstract fun bindingFirebaseDatasource(firebaseDataSource: FirebaseDataSourceImpl): FirebaseDataSource
}