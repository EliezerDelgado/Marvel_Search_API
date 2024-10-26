package com.eliezer.marvel_search_api.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.ClearCharactersDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.ClearComicsDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetResultsInsertCharactersInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetResultsInsertComicsInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersDatabaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsDatabaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.FavoriteIdCharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.FavoriteIdComicsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val favoriteIdComicsViewModel: FavoriteIdComicsViewModel,
    val favoriteIdCharactersViewModel: FavoriteIdCharactersViewModel,
    val comicsViewModel: ComicsViewModel,
    val charactersViewModel: CharactersViewModel,
    val charactersDatabaseViewModel: CharactersDatabaseViewModel,
    val comicsDatabaseViewModel: ComicsDatabaseViewModel,
    private val getResultsInsertCharactersInDatabaseUseCase: GetResultsInsertCharactersInDatabaseUseCase,
    private val getResultsInsertComicsInDatabaseUseCase: GetResultsInsertComicsInDatabaseUseCase,
): BaseViewModel() {
}