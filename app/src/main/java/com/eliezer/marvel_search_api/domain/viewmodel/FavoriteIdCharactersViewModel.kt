package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdCharactersUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteIdCharactersViewModel   @Inject constructor(
    private val getFavoriteIdCharactersUseCase: GetFavoriteIdCharactersUseCase,
) : BaseViewModel()  {
    private var _favoriteIdCharacters = MutableLiveData<ArrayList<Int>>()
    val favoriteIdCharacters: LiveData<ArrayList<Int>> get() = _favoriteIdCharacters

    fun getFavoriteIdCharactersList() =
        viewModelScope.launch {
            getFavoriteIdCharactersUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkCharactersListResult(it)
                }
        }

    private fun checkCharactersListResult(result: Result<ArrayList<Int>>)=
        result.fold(
            onFailure = { e ->
                _error.value = e
            },
            onSuccess = {
                _favoriteIdCharacters.postValue(it)
            }
        )

    fun resetFavoriteIdCharacters() {
        _favoriteIdCharacters  = MutableLiveData<ArrayList<Int>>()
    }
}