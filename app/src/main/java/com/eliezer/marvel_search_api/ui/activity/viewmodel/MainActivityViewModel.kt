package com.eliezer.marvel_search_api.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.ClearCharactersDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.ClearComicsDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetResultsInsertCharactersInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetResultsInsertComicsInDatabaseUseCase
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
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
    private val getResultsInsertCharactersInDatabaseUseCase: GetResultsInsertCharactersInDatabaseUseCase,
    private val getResultsInsertComicsInDatabaseUseCase: GetResultsInsertComicsInDatabaseUseCase,

    private val clearComicsDatabaseUseCase: ClearComicsDatabaseUseCase
): BaseViewModel() {

    private var _isClear  = MutableLiveData<Int>()
    val isClear: LiveData<Int> get() = _isClear

    fun clearFavoritesComicsList() =
        viewModelScope.launch {
            clearComicsDatabaseUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    clearComplete()
                }
        }
    private fun clearComplete()
    {
        var value =  _isClear.value  ?:0
        _isClear.value = ++value
    }
    fun resetIsClear() {
        _isClear  = MutableLiveData<Int>()
    }
}