package com.eliezer.marvel_characters.data.repository

import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.domain.repository.CharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetCharactersRepository (
    private val marvelRepository: CharactersRepository
){
    fun setListRepository(params: List<Character>){
        marvelRepository.setListCharacters(params)
    }
}