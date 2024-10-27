package com.eliezer.marvel_search_api.ui.activity.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.InsertCharactersInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.InsertComicsInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersDatabaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsDatabaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.FavoriteIdCharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.FavoriteIdComicsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val favoriteIdComicsViewModel: FavoriteIdComicsViewModel,
    val favoriteIdCharactersViewModel: FavoriteIdCharactersViewModel,
    val comicsViewModel: ComicsViewModel,
    val charactersViewModel: CharactersViewModel,
    val charactersDatabaseViewModel: CharactersDatabaseViewModel,
    val comicsDatabaseViewModel: ComicsDatabaseViewModel,
): BaseViewModel() {
}