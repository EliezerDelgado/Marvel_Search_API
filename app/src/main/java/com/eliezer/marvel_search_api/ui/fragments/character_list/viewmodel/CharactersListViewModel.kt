package com.eliezer.marvel_search_api.ui.fragments.character_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdCharactersUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersOffListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersOffNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eliezer.marvel_search_api.models.dataclass.Characters

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val setCharactersRepository : SetCharactersRepository,
    private val getListCharactersOffNameUseCase: GetListCharactersOffNameUseCase,
    private val getFavoriteIdCharactersUseCase: GetFavoriteIdCharactersUseCase,
    private val getListCharactersOffListIdsUseCase: GetListCharactersOffListIdsUseCase
): BaseViewModel()  {

    private var _listCharacter  = MutableLiveData<Characters>()
    val listCharacter: LiveData<Characters> get() = _listCharacter


    private var _favoriteIdCharacters  = MutableLiveData<ArrayList<Int>>()
    val favoriteIdCharacters: LiveData<ArrayList<Int>> get() = _favoriteIdCharacters

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getListCharactersOffNameUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacters(name,it)
                }
        }
    }
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
    fun getFavoriteComicsList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListCharactersOffListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _listCharacter.postValue(it)
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

    private fun onResultOfGetListCharacters(name: String, character: Characters) {
        setCharactersRepository.setListRepository(name,character)
        _listCharacter.postValue(character)
    }

    fun resetCharacters() {
        _listCharacter  = MutableLiveData<Characters>()
    }
}