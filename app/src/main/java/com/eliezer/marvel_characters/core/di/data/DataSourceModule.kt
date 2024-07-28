package com.eliezer.marvel_characters.core.di.data

import com.eliezer.marvel_characters.data.datasource.CharactersDatasource
import com.eliezer.marvel_characters.data.datasource.impl.CharactersDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun bindingLoginDatasource(loginDatasourceImpl: CharactersDatasourceImpl):CharactersDatasource
}