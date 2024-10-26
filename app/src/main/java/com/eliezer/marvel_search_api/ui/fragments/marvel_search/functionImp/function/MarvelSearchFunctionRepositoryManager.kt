package com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject

class MarvelSearchFunctionRepositoryManager @Inject  constructor(
    private val setComicsRepository: SetComicsRepository,
    private val setCharactersRepository: SetCharactersRepository
) {
    fun insertTmpCharacters(characters: Characters) =  setCharactersRepository.setListTmpCharacters(characters.search,characters)
    fun insertTmpComics(comics: Comics) = setComicsRepository.setListTmpComics(comics.search,comics)
}