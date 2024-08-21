package com.eliezer.marvel_characters.ui.fragments.marvel_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_characters.domain.usecase.GetListCharactersUseCase
import com.eliezer.marvel_characters.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_characters.domain.usecase.GetListComicsUseCase
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
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
                    onResultOfGetListCharacter(it)
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
                    onResultOfGetListComics(it)
                }
        }
    }

    private fun onResultOfGetListCharacter(characters: List<Character>){
        if(characters.isNotEmpty())
            setCharactersUseCase.setListRepository(0,characters)
        _sizeResult.postValue(characters.size)
    }

    private fun onResultOfGetListComics(comics: List<Comic>){
        if(comics.isNotEmpty())
           setComicsUseCase.setListRepository(0,comics)
        _sizeResult.postValue(comics.size)
    }

    fun resetSizeResult() {
        _sizeResult  = MutableLiveData<Int>()
    }
}
