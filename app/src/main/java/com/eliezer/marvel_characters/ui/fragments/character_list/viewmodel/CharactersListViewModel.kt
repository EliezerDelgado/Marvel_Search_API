package com.eliezer.marvel_characters.ui.fragments.character_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_characters.domain.usecase.GetListCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eliezer.marvel_characters.models.dataclass.Characters

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val setCharactersRepository : SetCharactersRepository,
    private val getCharacterUseCase: GetListCharactersUseCase,
): BaseViewModel()  {

    private var _listCharacter  = MutableLiveData<Characters>()
    val listCharacter: LiveData<Characters> get() = _listCharacter
    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getCharacterUseCase.invoke(name)
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

    private fun onResultOfGetListCharacters(name: String, character: Characters) {
        setCharactersRepository.setListRepository(name,character)
        _listCharacter.postValue(character)
    }

    fun resetCharacters() {
        _listCharacter  = MutableLiveData<Characters>()
    }
}