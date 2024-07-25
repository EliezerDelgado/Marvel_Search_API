package com.eliezer.marvel_characters.feature.fragments.character_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.domain.usecase.gets.GetListCharactersUseCase
import com.eliezer.marvel_characters.models.dataclass.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val charactersUseCase: GetListCharactersUseCase,
)  : BaseViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>> get() = _characterList
    fun loadHotelList(name: String) {
        viewModelScope.launch {
            charactersUseCase.execute(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = it }
                .collect { _characterList.value = it }
        }
    }
}