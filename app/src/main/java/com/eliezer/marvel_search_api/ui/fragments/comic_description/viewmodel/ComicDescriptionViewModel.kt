package com.eliezer.marvel_search_api.ui.fragments.comic_description.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListComicCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eliezer.marvel_search_api.models.dataclass.Characters

@HiltViewModel
class ComicDescriptionViewModel @Inject constructor(
    private val setCharactersRepository : SetCharactersRepository,
    private val getCharacterUseCase: GetListComicCharactersUseCase,
): BaseViewModel()  {

    private var _listCharacter  = MutableLiveData<Characters>()
    val listCharacter: LiveData<Characters> get() = _listCharacter
    fun searchCharactersList(comicId: Int) {
        viewModelScope.launch {
            getCharacterUseCase.invoke(comicId)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacters(comicId,it)
                }
        }
    }

    private fun onResultOfGetListCharacters(comicId: Int, character: Characters) {
        setCharactersRepository.setListRepository(comicId.toString(),character)
        _listCharacter.postValue(character)
    }

    fun resetCharacters() {
        _listCharacter  = MutableLiveData<Characters>()
    }
}