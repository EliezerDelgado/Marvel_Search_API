package com.eliezer.marvel_characters.core.modules

import com.eliezer.marvel_characters.data.repository.CharacterRepositoryImpl
import com.eliezer.marvel_characters.data.repository.GetCharactersRepository
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {
    @Binds
    abstract fun bindLoginRepository(impl: CharacterRepositoryImpl): CharactersRepository

}