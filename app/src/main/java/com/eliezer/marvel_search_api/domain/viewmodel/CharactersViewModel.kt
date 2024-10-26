package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByNameUseCase
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetListCharactersByNameUseCase,
    private val getListCharactersByListIdsUseCase: GetListCharactersByListIdsUseCase,
    ):BaseViewModel(){

    private var _characters= MutableLiveData<Characters>()
    val characters: LiveData<Characters> get() = _characters

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getCharactersUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                    _userErrorMessage.value = R.string.error_empty_search
                }
                .collect {
                    it.search = name
                    _characters.value = it
                }
        }
    }

    fun getFavoriteCharactersList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListCharactersByListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _characters.postValue(it)
                }
        }
    fun resetCharacters()
    {
        _characters = MutableLiveData<Characters>()
    }
}