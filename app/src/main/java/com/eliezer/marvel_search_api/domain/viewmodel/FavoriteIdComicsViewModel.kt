package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdComicsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteIdComicsViewModel @Inject constructor(
    private val getFavoriteIdComicsUseCase: GetFavoriteIdComicsUseCase
) :BaseViewModel(){
    private var _favoriteIdComics = MutableLiveData<ArrayList<Int>>()
    val favoriteIdComics: LiveData<ArrayList<Int>> get() = _favoriteIdComics

    fun getFavoriteIdComicsList() =
        viewModelScope.launch {
            getFavoriteIdComicsUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkComicsListResult(it)
                }
        }

    private fun checkComicsListResult(result: Result<ArrayList<Int>>)=
        result.fold(
            onFailure = { e ->
                _error.value = e
            },
            onSuccess = {
                _favoriteIdComics.postValue(it)
            }
        )

    fun resetFavoriteIdComics() {
        _favoriteIdComics  = MutableLiveData<ArrayList<Int>>()
    }
}