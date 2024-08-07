package com.eliezer.marvel_characters.core.di

import com.eliezer.marvel_characters.data.datasource.CharactersDataSource
import com.eliezer.marvel_characters.data.datasource.ComicsDataSource
import com.eliezer.marvel_characters.data.datasource.impl.CharactersDataSourceImpl
import com.eliezer.marvel_characters.data.datasource.impl.ComicsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun bindingCharactersDatasource(loginDatasourceImpl: CharactersDataSourceImpl):CharactersDataSource

    @Binds
    abstract fun bindingComicsDatasource(loginDatasourceImpl: ComicsDataSourceImpl): ComicsDataSource
}