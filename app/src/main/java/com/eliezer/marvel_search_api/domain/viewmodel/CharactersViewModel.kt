package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteListCharactersOnDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByComicUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByNameUseCase
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersViewModel @Inject constructor(
    private val getListCharactersByNameUseCase: GetListCharactersByNameUseCase,
    private val getListCharactersByComicUseCase: GetListCharactersByComicUseCase,
    private val getListCharactersByListIdsUseCase: GetListCharactersByListIdsUseCase,
    private val getFavoriteListCharactersOnDatabaseUseCase : GetFavoriteListCharactersOnDatabaseUseCase
    ):BaseViewModel(){

    private var _characters= MutableLiveData<Characters>()
    val characters: LiveData<Characters> get() = _characters

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getListCharactersByNameUseCase.invoke(name)
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

    fun searchCharactersList(comicId: Int) {
        viewModelScope.launch {
            getListCharactersByComicUseCase.invoke(comicId)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    it.search = comicId.toString()
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
    fun getFavoriteCharactersList() =
        viewModelScope.launch {
            getFavoriteListCharactersOnDatabaseUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    setListCharacter(it)
                }
        }


    private fun setListCharacter(list: List<Character>?) {
        list?.also {
            val character = Characters()
            character.total = it.size
            character.listCharacters.addAll(it)
            _characters.postValue(character)
        }
    }
    fun resetCharacters()
    {
        _characters = MutableLiveData<Characters>()
    }
}