package com.eliezer.marvel_characters.core.di

import com.eliezer.marvel_characters.data.repository.characters.CharacterRepositoryImpl
import com.eliezer.marvel_characters.data.repository.comics.ComicsRepositoryImpl
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.domain.repository.ComicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {
    @Binds
    abstract fun bindCharactersRepository(impl: CharacterRepositoryImpl): CharactersRepository

    @Binds
    abstract fun bindComicsRepository(impl: ComicsRepositoryImpl): ComicsRepository
}