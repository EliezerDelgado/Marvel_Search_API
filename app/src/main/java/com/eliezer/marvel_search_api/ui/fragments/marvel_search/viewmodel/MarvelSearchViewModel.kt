package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersUseCase
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsUseCase
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelSearchViewModel @Inject constructor(
    private val setCharactersUseCase : SetCharactersRepository,
    private val setComicsUseCase : SetComicsRepository,
    private val getCharactersUseCase: GetListCharactersUseCase,
    private val getComicsUseCase: GetListComicsUseCase,
)  : BaseViewModel() {

    private var _sizeResult  = MutableLiveData<Int>()
    val sizeResult: LiveData<Int> get() = _sizeResult

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getCharactersUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacter(it,name)
                }
        }
    }
    fun searchComicsList(title: String) {
        viewModelScope.launch {
            getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(it,title)
                }
        }
    }

    private fun onResultOfGetListCharacter(characters: Characters,name: String){
        if(characters.listCharacters.isNotEmpty())
            setCharactersUseCase.setListRepository(name,characters)
        _sizeResult.postValue(characters.listCharacters.size)
    }

    private fun onResultOfGetListComics(comics: Comics, title: String){
        if(comics.listComics.isNotEmpty())
           setComicsUseCase.setListRepository(title,comics)
        _sizeResult.postValue(comics.listComics.size)
    }

    fun resetSizeResult() {
        _sizeResult  = MutableLiveData<Int>()
    }

    fun resetLists() {
        setComicsUseCase.resetListRepository()
        setCharactersUseCase.resetListRepository()
    }
}
