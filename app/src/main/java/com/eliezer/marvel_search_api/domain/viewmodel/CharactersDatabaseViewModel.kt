package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.ClearCharactersDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.InsertCharactersInDatabaseUseCase
import com.eliezer.marvel_search_api.models.dataclass.Character
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersDatabaseViewModel @Inject constructor(
    private val clearCharactersDatabaseUseCase: ClearCharactersDatabaseUseCase,
    private val insertCharactersDatabaseViewModel: InsertCharactersInDatabaseUseCase,
) :  BaseViewModel() {

    private var _isClear  = MutableLiveData<Boolean>()
    val isClear: LiveData<Boolean> get() = _isClear

    private var  _isInserted =  MutableLiveData<List<Long>>()
    val isInserted: LiveData<List<Long>> get() = _isInserted

    fun clearFavoritesCharactersList() =
        viewModelScope.launch {
            clearCharactersDatabaseUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _isClear.value = true
                }
        }
    fun insertFavoritesCharactersList(characters : ArrayList<Character>) =
        viewModelScope.launch {
            insertCharactersDatabaseViewModel.invoke(characters)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _isInserted.value = it
                }
        }
    fun resetIsClear() {
        _isClear  = MutableLiveData<Boolean>()
    }
    fun resetIsInserted() {
        _isInserted  = MutableLiveData<List<Long>>()
    }
}