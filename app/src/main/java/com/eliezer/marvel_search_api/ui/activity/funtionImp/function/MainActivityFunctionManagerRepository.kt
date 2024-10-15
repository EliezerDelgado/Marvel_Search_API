package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic
import javax.inject.Inject


class MainActivityFunctionManagerRepository @Inject constructor(
    private val setComicsRepository: SetComicsRepository,
    private val setCharactersRepository: SetCharactersRepository
)
{
    fun insertDatabaseComics(comics: List<Comic>) = setComicsRepository.setListInRoomDatabase(comics)
    fun clearDatabaseComic() = setComicsRepository.clearListRoomDatabase()
    fun insertDatabaseCharacters(characters: List<Character>) = setCharactersRepository.setListRoomDatabase(characters)
    fun clearDatabaseCharacter() = setCharactersRepository.clearListRoomDatabase()
}