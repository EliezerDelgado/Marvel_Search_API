package com.eliezer.marvel_search_api.ui.fragments.character_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteListCharactersOnDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Character

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val setCharactersRepository : SetCharactersRepository,
    private val getListCharactersByNameUseCase: GetListCharactersByNameUseCase,
    private val getFavoriteListCharactersOnDatabaseUseCase : GetFavoriteListCharactersOnDatabaseUseCase
): BaseViewModel()  {

    private var _listCharacter  = MutableLiveData<Characters>()
    val listCharacter: LiveData<Characters> get() = _listCharacter

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getListCharactersByNameUseCase.invoke(name)
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
            _listCharacter.postValue(character)
        }
    }

    private fun onResultOfGetListCharacters(name: String, character: Characters) {
        setCharactersRepository.setListTmpCharacters(name,character)
        _listCharacter.postValue(character)
    }

    fun resetCharacters() {
        _listCharacter  = MutableLiveData<Characters>()
    }
}